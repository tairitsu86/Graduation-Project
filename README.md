Graduation Project--
Connect IM and IoT By Microservice
===


# Introduction


## Table of Contents

[TOC]



## Basic function

## Ingenious design
### 採用複合的溝通模式  
由於微服務將系統由商業邏輯分割成數個區塊，使用者的單一功能可能都會調動多個服務，此時採用同步堵塞的api溝通會造成效率低下及容錯率下降，所以設計上會選擇事件驅動架構。  
但同時，事件驅動架構有著無回傳的特性，如果全採用事件驅動架構會導致步驟過於瑣碎，增加錯誤發生率。  
綜合以上，除非是溝通上必須或取回傳值（如資料庫查詢），其餘部分皆採用事件驅動。

### 簡易iot裝置協定  
有鑑於讓使用者親自新增iot裝置有些不便與麻煩，所以本專案決定以簡單握手協定，讓裝置能自動註冊到系統上，兩者間也能以最低成本進行溝通。

#### hand shake
1. 裝置將透過本系統api獲得系統device Id、mqtt broker host位置等訊息
    
```sequence
Device-->System: API Request
System->Device: Response Mqtt broker data, device id=123
``` 
2. 裝置透過info主題上傳自身基本資料，並訂閱自己的control主題，並初始化所有state資料
```sequence
Device-->MQTT broker: subscribe devcies/123/control
Device-->MQTT broker: publish info to devices/info
MQTT broker-->System: send device info
```
3. 握手完成，系統透過特定control主題控制裝置，而裝置透過state主題傳送各種狀態訊息
```sequence
Note left of System: Turn on the light
System-->MQTT broker: publish to devcies/123/control
MQTT broker-->Device: send control event
Note right of Device: Do something
Device-->MQTT broker: publish to devices/state
MQTT broker-->System: send state event
Note left of System: Know the light is on
```
#### MQTT Topic範例:
1. devices/info
2. devices/state
3. devcies/{deviceId}/control


devices/info example:
```
{
  "deviceId": "118lqy",
  "deviceName": "test device",
  "description": "This is a test device, made by ESP32!",
  "owner": "OAO",
  "states": [
    {
      "stateId": 0,
      "stateType": "PASSIVE",
      "dataType": "OPTIONS",
      "stateName": "light power",
      "stateOptions": [
        "on",
        "off"
      ]
    },
    {
      "stateId": 1,
      "stateType": "PASSIVE",
      "dataType": "ANY",
      "stateName": "LCD text"
    },
    {
      "stateId": 2,
      "stateType": "ACTIVE",
      "dataType": "ANY_FLOAT",
      "stateName": "temperature",
      "valueUnit": "°C"
    },
    {
      "stateId": 3,
      "stateType": "ACTIVE",
      "dataType": "ANY_FLOAT",
      "stateName": "humidity",
      "valueUnit": "%"
    }
  ],
  "functions": [
    {
      "functionId": 0,
      "functionName": "light switch",
      "dataType": "OPTIONS",
      "functionOptions": [
        "on",
        "off"
      ]
    },
    {
      "functionId": 1,
      "functionName": "LCD show text",
      "parameterName": "any string",
      "dataType": "ANY"
    }
  ]
}

```

devices/state example:
```
{
  "executor": "OAO",
  "deviceId": "118lqy",
  "stateId": 0,
  "stateValue": 0
}

{
  "deviceId": "118lqy",
  "stateId": 2,
  "stateValue": 25.73
}
```
devcies/{deviceId}/control example:
```
{
  "deviceId": "118lqy",
  "executor": "OAO",
  "functionId": 0,
  "parameter": 0
}
```

### IMIG-IMUI 溝通格式  
有鑑於我們的目標是各種IM平台皆可以使用，為了達到這個目的，設計上將單一IM平台的所有功能包裝在一個服務中，所以為了簡單維護與提高擴展性，這裡訂定了一個IM微服務應該提供怎麼樣的服務，因為IM平台在本專題作為一個UI，該服務只用於實現通訊部分，其餘商業邏輯由其他系統負責。

#### 接收訊息
實作該IM平台之Webhook接收功能，並將使用者訊息打包成統一格式，統一格式範例:
```
{
  "message": "message from user",
  "imUserData": {
    "platform": "IM platform name",
    "userId": "IM platform user id"
  }
}
```
再透過Rabbit MQ傳送到IM-UI-System.IM-User-Message佇列中。

#### 傳送訊息
透過訂閱Rabbit MQ特定主題(IM-Integration-System.{IM平台名稱})，接收並能解析本系統給予的訊息格式，本系統訊息格式範例:
```
{
    "userIdList": ["IM platform user id", "IM platform user id", "IM platform user id"],
    "message": "message to user"
}
```
再透過該IM平台之訊息寄送功能，將訊息寄送給使用者。


### github branch管理  
微服務架構在專案版本管理上有很多不同的流派，常見的有三種，一種是讓每個服務擁有獨立的儲存庫，另一個是全部放在一個儲存庫，還有中庸之道，把邏輯相近的服務放在同個儲存庫，然後整個專案就只有幾大儲存庫。  
以上方法各有利弊，這裡只講我們選擇的大泥球模式，也就是將所有服務放在同一個儲存庫。  
這樣做遇到的問題主要有以下幾個  
* 合作困難  
* CI/CD 建置不易  
* 儲存庫內情況複雜  
    
但我們不是第一個這麼做的，解決方法早已存在，在設計上我們讓每個服務擁有自己的兩個分支，deploy與release，deploy分支用於普通開發，而一個階段完成後，會將其合併至release分支，另外還有最後的master分支。  
這樣做很多事情都簡單很多，合作上各自只需要認領自己的分支，而cicd的部分，github action能夠指定分支與動作，所以可以對每個分支進行各自的操作。  
最後儲存庫內的情況，因為大家自處理自己的分支，最後合併於master，所以並不會搞得太複雜，clone時單一分支下面也只有自己的專案檔案，現在唯一複雜的東西是branch，現在有(2n)+1個branch，n由服務的數量決定，看起來蠻多的。  

### 使用者事件  
使用者事件(以下簡稱為事件)是IMUI系統中處理使用者訊息的核心設計，目的在於建立足夠便利、彈性的服務取用手段。  
為了避免在程式內寫死的直接耦合，使用者事件採用能夠隨時新增、更改的設定檔來取得服務，將耦合從程式碼的層級剝離，雖然在執行階段兩者的耦合性並無區別，但在開發、部屬、更新以及後續維護上，事件的設計都具有更好的彈性，像是在遇到API格式變更時，就無需更動程式碼，而是只需要更新事件的設定檔即可。  



#### 使用者事件設定檔
事件基本上能視為一次的API Request發送以及Respnse的處裡，以下是一個事件的範例： 
```
{
  "eventName": "GROUP_BROADCAST",
  "descriptionTemplate": "Type some message to the members of this group!",
  "variables": [
    {
      "displayNameTemplate": "",
      "variableName": "MESSAGE"
    }
  ],
  "commConfig": {
    "methodType": "GET",
    "urlTemplate": "http://user-database-service:8000/users/${USERNAME}",
    "headerTemplate": {},
    "bodyTemplate": {},
    "errorTemplate": {}
  },
  "respondConfig": {
    "respondType": "NOTIFY",
    "respondData": {
      "respondTemplate": "User [${USER_DISPLAY_NAME}] broadcast message in group [${GROUP_NAME}]:\n${MESSAGE}",
      "notifyVariables": [
        {
          "variableName": "USER_DISPLAY_NAME",
          "jsonPath": "$.userDisplayName"
        },
        {
          "variableName": "GROUP_LIST",
          "jsonPath": "PAST_PARAMETER GROUP_ID"
        }
      ]
    }
  }
}
```
> 本專案中群組內廣播的設定檔  

在開始解釋前，需要先了解另一個設計**模板變數**  

**模板變數(Template variable)**  
模板變數是為了將各種不同的資料填入設定檔中的文本模板而設計的，所有文本模板皆以Template結尾，而該欄位也被稱呼為模板欄位，請參考以下範例：  
```
"messageTemplate": "Hello! ${USER}!"
```
此時messageTemplate即為模板欄位，而後面內容中的${USER}為放置模板變數值的特殊格式，用"\${}"包起來的是模板變數的key，模板變數是以鍵值對(key-value)的方式儲存的。  
此時假設key為USER的模板變數值為John，填入後就會獲得句子。  
```
"Hello! John!"
```
以上就是模板變數的簡略說明，變數名基本上可以隨意取，不過要避開下列系統模板變數。  

**系統模板變數**
| Variable Name     | Description        | Work On    | Remark                     |
|:----------------- |:------------------ |:---------- |:-------------------------- |
| PLATFORM          | IM平台名稱         | 所有事件   | 在事件新增時自動加入       |
| USER_ID           | IM平台User id      | 所有事件   | 在事件新增時自動加入       |
| EVENT_NAME        | 事件名稱           | 所有事件   | 在事件新增時自動加入       |
| USERNAME          | 本系統username     | 所有事件   | 在事件新增時自動加入       |
| USER_LIST         | username清單       | notify事件 | 加入此清單的user會收到通知 |
| GROUP_LIST        | group id清單       | notify事件 | 加入此清單的群組會收到通知 |
| NEXT_EVENT        | 指定下個事件       | menu事件   | 指定單獨選項的下個事件     |
| INT_LAST_SELECTED | 上次menu事件選擇 | 所有事件   | 在menu事件結束時自動加入   |
| VARIABLE_DISPLAY_NAME | 變數名稱 | 所有事件   | 在變數敘述產生前自動加入   |

**不同類型的模板變數**  
Json支援的類型很廣，只有String肯定是不夠用的，所以本專案額外實作了其他常見資料類型：Integer、Boolean和Float。  
只有在bodyTemplate中他們會有不同的填入方式，其餘地方完全相同

**String模板變數填入bodyTemplate範例:**
```
{
    "StringTemplate":"${MyString}"
}
```
> 其中${MyString}的部分會被名為MyString的變數值取代

假設MyString的值為"Hello!"，則結果為:
```
{
    "StringTemplate":"Hello!"
}
```

**Integer模板變數填入bodyTemplate範例:**
```
{
    "IntegerTemplate":"${INT_MyInteger}"
}
```
> 其中"${INT_MyInteger}"的部分會被名為INT_MyInteger的變數值取代  
> Integer模板變數必須以INT_開頭，否則會被視為String  

假設INT_MyInteger的值為123456，則結果為:
```
{
    "IntegerTemplate":123456
}
```

**Boolean模板變數填入bodyTemplate範例:**
```
{
    "BooleanTemplate":"${BOOL_MyBoolean}"
}
```
> 其中"${BOOL_MyBoolean}"的部分會被名為BOOL_MyBoolean的變數值取代  
> Boolean模板變數必須以BOOL_開頭，否則會被視為String  

假設BOOL_MyBoolean的值為true，則結果為:
```
{
    "BooleanTemplate": true
}
```
**Float模板變數填入bodyTemplate範例:**
```
{
    "FloatTemplate":"${FLOAT_MyFloat}"
}
```
> 其中"${FLOAT_MyFloat}"的部分會被名為FLOAT_MyFloat的變數值取代  
> Boolean模板變數必須以FLOAT_開頭，否則會被視為String  

假設FLOAT_MyFloat的值為12.34，則結果為:
```
{
    "FloatTemplate": 12.34
}
```

以上是模板變數的完整說明。  

**使用者事件設定檔介紹**  

在事件的設定檔中，大致能分成三塊，基本設置、API請求以及API回應  

**1. 基本設置**
```
{
  "eventName": "GROUP_BROADCAST",
  "descriptionTemplate": "Type some message to the members of this group!",
  "variables": [
    {
      "displayNameTemplate": "",
      "variableName": "MESSAGE"
    }
  ]
}
```
eventName是事件的名稱，也是事件的唯一識別名。  
descriptionTemplate在variables不是空陣列時才有作用，用於產生詢問的使用者的句子。  
variables內存放的是需要使用者輸入的資料。  
displayNameTemplate是用於顯示的變數名。  
variableName是使用者輸入資料後，將以variableName作為key儲存資料。  
本範例中沒有使用到displayNameTemplate，如果使用的話會像這樣：  
```
{
  "eventName": "GROUP_BROADCAST",
  "descriptionTemplate": "Type ${VARIABLE_DISPLAY_NAME} to the members of this group!",
  "variables": [
    {
      "displayNameTemplate": "some message",
      "variableName": "MESSAGE"
    }
  ]
}
```
使用者依舊會收到"Type some message to the members of this group!"。  

**2.API請求**  
```
  "commConfig": {
    "methodType": "GET",
    "urlTemplate": "http://user-database-service:8000/users/${USERNAME}",
    "headerTemplate": {},
    "bodyTemplate": {},
    "errorTemplate": {}
  },
```
methodType為Http method，支援POST、DELETE、PUT、GET和一個本系統的MQ，會改由Rabbit MQ發送事件。  
urlTemplate為API url 模板欄位，或是MQ的topic名稱。  
headerTemplate為API header之模板欄位。  
bodyTemplate為API request body或MQ事件之模板欄位。  
errorTemplate為API response code並非2xx時，會試著在這裡尋找能回報給使用者的訊息，例如：  
```
"errorTemplate": {
      "404": "Group with id ${GROUP_ID} not found!"
    }
```
當API獲得404回傳時，就會將該訊息Group with id ${GROUP_ID} not found!回應給使用者。
如果API response code是2xx時，則進入事件最後一個區塊 -- **API回應**。  

**3.API回應**  
```
  "respondConfig": {
    "respondType": "NOTIFY",
    "respondData": {
      "respondTemplate": "User [${USER_DISPLAY_NAME}] broadcast message in group [${GROUP_NAME}]:\n${MESSAGE}",
      "notifyVariables": [
        {
          "variableName": "USER_DISPLAY_NAME",
          "jsonPath": "$.userDisplayName"
        },
        {
          "variableName": "GROUP_LIST",
          "jsonPath": "PAST_PARAMETER GROUP_ID"
        }
      ]
    }
  }
```
respondType有三種，單純回傳訊息的NOTIFY、回傳選單事件的MENU和當單一API無法完成事件，需要發送更多API時使用的UNFINISHED，而respondData會因為不同的respondType而改變。  
這裡只介紹相對簡單的NOTIFY  
respondTemplate是回應使用者的文本模板。
notifyVariables是要從剛剛API的Respose Body獲取資料的變數，透過jsonPath的值對Respose Body曲值，並以variableName為key儲存。  
jsonPath可以用PAST_PARAMETER來取得舊有參數。




## Information security considerations

資料庫專用帳號與預存程序權限管理

密碼採用Hash儲存

IM Webhook驗證

不對外開放API

## Performance tuning and monitoring

資料庫查詢優化

K8S 監控
服務網格管理
MQ監視

壓力測試

## DevOps
1. 計畫  
最重要也最難描述的階段，嗯……就是想一想該怎麼做、能怎麼做與我們怎麼做，軟體工程中有一句話：沒有最好的設計，只有別無選擇的設計。
世界上沒有完美的設計，我們需要深入了解該專案的需求，然後為它訂製一套架構，我喜歡別無選擇這個形容詞，每個架構都有自己的優缺點，而選擇的關鍵在於該專案不可失去的優點與不可多得的缺點，將一個又一個的設計去除，最後那個別無選擇的選項就是現在的最佳解。

3. 實作  
系統實作上採用java spring boot框架，該框架有很多部分，我們主要使用web的框架。
資料庫選擇SQL Server，兩者之間透過資料持久化技術JPA連接。
第三方套件使用maven進行管理。
iot裝置上樹莓派使用python，esp32使用c++進行開發。



5. 測試  
自動測試的部分有兩個，一個是spring自帶的編譯測試，基本上測專案是否能打包而已，另外是寫了很少的單元測試，因為過於耗時，所以並沒有。  
自動測試交由github action使用maven工具進行，而測試時機是將程式碼透過git推送到deploy分支便會進行。
整合測試與點到點測試並沒有寫成自動化，純人工測試。

7. 建置  
建置一樣由github action進行，使用maven工具打包成單一壓縮檔(.jar)，再用docker包裝成映像檔推送至docker hub，而建置時機為當deploy分支合併至release分支。

9. 部署  
目前採用手動部署，透過手動啟動windows腳本檔(.bat)即可，部署環境為kubernetes

11. 監控  
窩不知道

13. 驗收  
壓力測試？



## difficulties encountered
* MQTT難以負載平衡
MQTT是一個簡易的溝通協定，但也是因為其過於簡易，大多MQTT Broker不提供競爭者模式，所謂競爭者模式是讓多個消費者搶奪一個事件，讓該事件只有一個消費者獲得，也是大多事件驅動架構用於負載平衡的特性。
所以如果單純複製MQTT管理端程式，只會導致同個事件被執行數次，對效能並沒有任何幫助。
解決方式有很多，但各有優缺點
    1. 透過一個外部的負載平衡器
    算是最簡單也最優的解答，透過負載平衡器接收MQTT的事件，再將事件分給業務端，缺點是MQTT流量過大負載平衡器自身不能負載平衡，所以只能算是轉移問題，但沒有解決問題。
    2. 透過Hash來進行負載平衡
    可以透過時間戳記等資料進行Hash，決定資料送給哪個管理端點，實作上非常容易，但有個問題是

## FQA
* **為何不用gRpc?**  
    1.時間問題  
    2.設計上並不適合使用  (?  

* **為何不直接做網頁?**  
確實如果只考慮設計時的初衷，網頁比IM平台的聊天機器人實作更簡單、設計更容易、使用上也更方便

## 未來展望
### IM IG方面
1. 提供更好的介面
   像是利用IM平台自帶的按鈕選單來提供更好的介面，或是找到其他實現方法。
    
### IM UI方面
1. 結合AI
   讓使用者能透過正常的自然語言控制裝置，像是現在市面上已經存在的那些聲控智慧家電的文字訊息版本，但不同的點在於，只要有手機就能實現操控，也無需額外購買智慧家電的中控裝置。
2. 提供更多語言
   因為之前想投稿外國期刊，所以系統是英文介面，但一個真正優秀的系統應該要能有多國語言，但目前架構上文字是寫死的，要修改可能又得花費大量精力，但是我們有個好幫手，AI可以進行翻譯。

### IoT方面
1. 結合市面上的智慧家電產品
2. 定義更良好的IoT協定
   目前的協定都只著重於溝通的部分，但其實還有很多應該加入的部分，例如加入心跳機制讓控制系統能確認IoT裝置的運行是否正常等等
3. 解決MQTT的負載平衡問題
### 

JAVA問題
Use final injection will let it can't find 0 args constructor, then go wrong.
This error only happen when final injection(with @RequiredArgsConstructor/ Constructor with contains all final field)and @builder(with @AllArgsConstructor) both exist, and we want creating a bean by @Component.
But use @Autowired didn't, why?

## How to deployment the project?

# System Introduction
整該專案即為主系統  
主系統下有若干個根據邏輯業務命名的子系統  
每個子系統內包含數個不同的服務  

## Sub System List

| Sub System Name       | abbr. | Despription          |
| --------------------- | ----- | -------------------- |
| IM-Integration-System | IMIG  | 負責各IM平台的通訊   |
| IM-UI-System          | IMUI  | 負責IM用戶的服務邏輯 |
| Login-System          | LOGIN | 負責系統用戶的管理   |
| IoT-System            | IoT   | 負責物聯網設備的通訊 |

### IM-Integration-System (IMIG)
全稱Instant Messaging Integration System(即時通訊軟體整合系統，以下簡稱IMIG)  
用於介接各種即時通訊軟體(以下簡稱IM)之聊天機器人，將不同的IM透過定義好的溝通格式一致化，提供良好的IM擴充性，以及降低後續商業邏輯處裡的成本，核心系統無須為了任何IM平台進行特化。

### IM-UI-System (IMUI)
全稱Instant Messaging User Interface System(即時通訊軟體使用者介面系統，以下簡稱IMIG)  
用於處理IM用戶的不同文字訊息，解析並提供服務和回饋給使用者。  
以IMUI命名的目的在於強調IM不應該帶有任何的商業邏輯，應該由系統核心處理，IM只作為使用者和系統的橋樑。  
原本設計上IMIG被包含在IMUI中，但後來決定將商業邏輯和IM介接分離。

### Login-System (LOGIN)
全稱Login System(登入系統，以下簡稱LOGIN)  
用於處理本系統帳戶關係與管理的系統，範圍包括註冊、登入、登出、群組管理等。  
原本設計上只是想要一個登入系統的功能，所以直接Login System命名，等後續需要更多功能時，經過考量後，認為群組等功能和登入系統都和系統使用者有關，故放在同個子系統。  
應該以使用者管理系統命名更為準確，但改名有點麻煩，所以維持Login-System的名字。

### IoT-System (IoT)
全稱Internet of Things System(物聯網系統，以下簡稱IoT)  
用於管理、連接和控制本系統製作的簡易物聯網裝置。  
本計畫的初衷是控制市面上的智慧家電，但有鑑於經費、可能不支援API控制等種種不確定因素，最終決定先使用確認可行的ESP32、樹梅派裝置實作，後續若時間允許再嘗試智慧家電。

## Service List

| Service Name     | Sub System | Port | Node Port | State  |
| ---------------- | ---------- | ---- |:--------- |:------ |
| user-database    | LOGIN      | 8000 | 30000     | done   |
| webhook-handler  | IMUI       | 8001 | 30001     | remove |
| login-tracker    | IMUI       | 8002 | 30002     | done   |
| message-sender   | IMUI       | 8003 | 30003     | done   |
| event-handler    | IMUI       | 8004 | 30004     | done   |
| device-connector | IoT        | 8005 | 30005     | done   |
| device-database  | IoT        | 8006 | 30006     | done   |
| event-executor   | IMUI       | 8007 | 30007     | done   |
| group-manager    | LOGIN      | 8008 | 30008     | done   |
| line-service     | IMIG       | 8009 | 30009     | done   |
| telegram-service | IMIG       | 8010 | 30010     | done   |
| test-console     | IMIG       | 8011 | 30011     | done   |

### user-database
紀錄與儲存本系統用戶資料，以及負責登入登出等相關服務。

### webhook-handler
原本設計上會在這裡實作各IM的webhook接收API，但各家的webhook設計、request body格式等細節可謂大相逕庭，無法用簡單的程式統一接收。  
若為了不同平台實作獨立的程式碼，最終將導致此服務越來越大，增加錯誤率的同時也在各IM間增加了不需要的耦合性，所以後來移除了此設計，改為由每個IM的服務接收。

### login-tracker
用於存放IM ID和本系統ID的對照表，以便確認使用者狀態以及發送訊息。  
他會接收user-database所發佈的登入登出事件，以此紀錄哪個用戶登入了，登入了什麼帳號等等，提供查找服務。

### message-sender
負責將本系統欲傳送給用戶之訊息，送給正確的IM帳號，具體上就是它會收到本系統的user帳號，透過login-tracker轉換為IM平台帳號，再轉交給該IM平台的服務。

### event-handler
負責解析使用者訊息、查詢使用者狀態、向使用者蒐集參數等等，獲得該事件所需之參數後，將資料交給event-executor以進行下一步地處理。

### event-executor
接收來自event-handler的事件與參數，依照設定檔進行處理與回傳。

### device-connector
負責與裝置的直接連接、控制和獲取狀態，並記錄裝置狀態歷史，與新裝置hand shake等等。

### device-database
負責權限控管、提供裝置訊息等。

### group-manager
提供群組這一抽象概念的服務，包含新增、刪除、搜尋、加入及退出群組等服務。

### line-service
透過Line的webhook接收使用者訊息，以及透過Line api寄送訊息給使用者。

### telegram-service
透過Telegram的webhook接收使用者訊息，以及透過Telegram api寄送訊息給使用者。

### test-console
測試用的介面，透過實作IMIG系統之溝通協定，模擬使用者透過IM發送訊息的網頁程式。


## Rabbit Message Queue
### Exchange List

| Exchange Name                                      | Abbr.    | Description          |
|:-------------------------------------------------- |:-------- |:-------------------- |
| IoT-System_Device-base-communication_Exchange      | IoT_DBC  | 負責系統-裝置溝通    |
| IoT-System_User-base-communication_Exchange        | IoT_UBC  | 負責用戶-系統溝通    |
| IM-UI-System_IM-base-communication_Exchange        | IMUI_IBC | 負責IM平台-系統溝通  |
| IM-UI-System_REST-API-event_Exchange               | IMUI_RAE | 負責用戶事件         |
| Login-System_User-access_Exchange                  | LOGIN_UA | 負責用戶的登入與登出 |
| System_Notification_Exchange                       | SYS_NTF  | 負責各類通知訊息     |
| System_Service_Exchange                            | SYS_SVC  | 用於取用系統內部服務 |
| IM-Integration-System_IM-platform-service_Exchange | IMIG_IPS | 用於取用IM平台服務   |




### Queue List

| Queue Name                     | Binding          | Description                  |
|:------------------------------ |:---------------- |:---------------------------- |
| IoT-System.Device-Control      | IoT_DBC          | 傳送裝置控制指令             |
| IoT-System.Device-Info         | IoT_DBC          | 傳送裝置資訊                 |
| IoT-System.Device-State        | IoT_DBC          | 通知使用者裝置狀態改變       |
| IoT-System.User-Control        | IoT_UBC, SYS_SVC | 傳送用戶的控制指令           |
| IM-UI-System.Send-Message      | IMUI_IBC         | 透過IM平台寄送訊息給使用者   |
| IM-UI-System.IM-User-Message   | IMUI_IBC         | 來自任意IM的用戶訊息         |
| IM-UI-System.Execute-Event     | IMUI_RAE         | 傳送取用REST API時所需的參數 |
| IM-UI-System.New-Event         | IMUI_RAE         | 產生新的IM用戶事件           |
| Login-System.Login-Log         | LOGIN_UA         | 當有用戶登入時發出登入訊息   |
| Login-System.Logout-Log        | LOGIN_UA         | 當有用戶登出時發出登出訊息   |
| IM-UI-System.Notify-User       | SYS_NTF          | 將通知訊息傳給用戶           |
| IM-Integration-System.LINE     | IMIG_IPS         | 負責LINE平台的訊息發送       |
| IM-Integration-System.TELEGRAM | IMIG_IPS         | 負責TELEGRAM平台的訊息發送   |
| IM-Integration-System.TEST     | IMIG_IPS         | 負責測試平台的訊息發送       |




###### tags: `Microservice` `Java Spring` `Kubernetes` 
