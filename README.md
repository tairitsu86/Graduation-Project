Graduation Project--
Connect IM and IoT By Microservice
===


# Introduction


## Table of Contents

[TOC]



## Basic function

## Ingenious design
1. 採用複合的溝通模式
由於微服務將系統由商業邏輯分割成數個區塊，使用者的單一功能可能都會調動多個服務，此時採用同步堵塞的api溝通會造成效率低下及容錯率下降，所以設計上會選擇事件驅動架構。
但同時，事件驅動架構有著無回傳的特性，如果全採用事件驅動架構會導致步驟過於瑣碎，增加錯誤發生率。
綜合以上，除非是溝通上必須或取回傳值（如資料庫查詢），其餘部分皆採用事件驅動

1. 簡易iot裝置協定
介於先前的iot裝置在與系統的連接過於麻煩，造成系統的擴展性不良，維護不易，所以訂定簡單協定，讓裝置與系統能以最低成本進行溝通。


1. 簡易IM微服務協定
有鑑於我們的目標是各種IM平台皆可以使用，為了達到這個目的，設計上將單一IM平台的所有功能包裝在一個服務中，所以為了簡單維護與提高擴展性，這裡訂定了一個IM微服務應該提供怎麼樣的服務，因為IM平台在本專題作為一個UI，該服務只用於實現通訊部分，其餘商業邏輯由其他系統負責。

1. 模擬網頁的設計
由於透過IM來控制各類裝置，甚至於提供其他服務對我們來講十分展新，第一個版本將過多的商業邏輯集中在與平台有關的服務上，最終導致服務龐大，維護不易。
而為了改進這個缺點，我們開始構思如何設計一個足夠有彈性的架構，而在思索中找到的答案就是網頁。
不同的瀏覽器能都夠運行同樣的網頁，這正是我們想要的，如果我們的系統能像網頁伺服器，將服務作為頁面，投射到名為IM平台的瀏覽器上，那我們變是一個足夠良好的架構了。
當然，IM平台是妥妥的純文字，不可能用html來撰寫，所以如何在純文字系統上加上功能性變成了現在最大的難題。
回到網頁，網頁是怎麼提供功能的？如何從一個介面到另一個介面的？更多網址！這就是答案，我們需要做的是利用文字系統發送api來獲取服務，其中最大的難點在於用戶如何給予api所需的參數？
沒有人喜歡用手機打一大串json或是寫一大串指令就為了不想起床走兩步路關燈，所以我們改用問答的方式，讓使用者階段性的提供資料，蒐集完成再取用api服務，便完成了一次單向溝通。
現在更大的問題來了，這個MVC系統，M由整個系統後段組成，C由收集並發送api的服務組成，而V的部分沒有實作。
前面問答的部分，雖然可能語意固定，過於冰冷，但總歸是合理的英文語句，還是堪用，但回傳的部分呢？沒有人會喜歡看一堆json或xml然後推測剛剛發生了什麼事情，所以我們也在發送api的服務上增加了一個用於將json轉換成正常語言的格式器，至此，此系統最麻煩也是最核心的部分才告一段落。
你可能會擔心，這樣的系統擁有足夠的彈性嗎？看起來api路徑參數跟回傳的格式器都需要寫死，確實，那些東西是死的，但不代表系統也得寫死，我把這整個從蒐集api參數到發送api和最後的給與回覆的行為稱為事件，而這個事件是能夠透過api新增與修改的，任何api服務都能夠隨時更新到系統上，而不會動到系統本身。

1. github branch管理
微服務架構在專案版本管理上有很多不同的流派，常見的有三種，一種是讓每個服務擁有獨立的儲存庫，另一個是全部放在一個儲存庫，還有中庸之道，把邏輯相近的服務放在同個儲存庫，然後整個專案就只有幾大儲存庫。
以上方法各有利弊，這裡只講我們選擇的大泥球模式，也就是將所有服務放在同一個儲存庫。
這樣做遇到的問題主要有以下幾個
    * 合作困難
    * CI/CD 建置不易
    * 儲存庫內情況複雜
    
    但我們不是第一個這麼做的，解決方法早已存在，在設計上我們讓每個服務擁有自己的兩個分支，deploy與release，deploy分支用於普通開發，而一個階段完成後，會將其合併至release分支，另外還有最後的master分支。
    
    這樣做很多事情都簡單很多，合作上各自只需要認領自己的分支，而cicd的部分，github action能夠指定分支與動作，所以可以對每個分支進行各自的操作。
最後儲存庫內的情況，因為大家自處理自己的分支，最後合併與master，所以並不會搞得太複雜，clone時單一分支下面也只有自己的專案檔案，現在唯一複雜的東西是branch，現在有(2n)+1個branch，n由服務的數量決定，看起來蠻多的。


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
自動測試的部分有兩個，一個是spring自帶的編譯測試，基本上測專案是否能打包而已，另外是寫了很少的單元測試，因為過於耗時，所以並沒有採用測試驅動開發TDD來做為此專案的開發模式。
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

* **為何要模仿網頁?而不直接做網頁?**
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

# System config

## Service
### Sub System List

| Sub System Name       | abbr. | Despription          |
| --------------------- | ----- | -------------------- |
| IM-Integration-System | IMIG  | 負責各IM平台的通訊   |
| IM-UI-System          | IMUI  | 負責IM用戶的服務邏輯 |
| Login-System          | LOGIN | 負責系統用戶的管理   |
| IoT-System            | IoT   | 負責物聯網設備的通訊 |



### Service List

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
### webhook-handler
### login-tracker
### message-sender
test data:
```
{
  "imUserDataList": [
    {
      "platform": "LINE",
      "userId": ""
    }
  ],
  "usernameList": [
    "OAO"
  ],
  "message": "HiHi"
}
```

### event-handler
#### TEST_NOTIFY
Customize event(notify):
```
{
  "eventName": "TEST_NOTIFY",
  "description": "Please enter %s for testing!",
  "variables": [
    {
      "displayNameTemplate": "template test",
      "variableName": "TEMPLATE_TEST"
    },
    {
      "displayNameTemplate": "url end point ${TEMPLATE_TEST}",
      "variableName": "URL_END_POINT"
    },
    {
      "displayNameTemplate": "test header",
      "variableName": "TEST_HEADER"
    },
    {
      "displayNameTemplate": "test string",
      "variableName": "TEST_STRING"
    },
    {
      "displayNameTemplate": "test boolean",
      "variableName": "BOOL_TEST_BOOLEAN"
    },
    {
      "displayNameTemplate": "test integer",
      "variableName": "INT_TEST_INTEGER"
    }
  ],
  "commConfig": {
    "methodType": "POST",
    "urlTemplate": "http://127.0.0.1:8080/${URL_END_POINT}",
    "headerTemplate": {
      "Content-Type": "application/json",
      "testHeader": "${TEST_HEADER}"
    },
    "bodyTemplate": {
      "testString": "${TEST_STRING}",
      "testBoolean": "${BOOL_TEST_BOOLEAN}",
      "testInteger": "${INT_TEST_INTEGER}"
    }
  },
  "respondConfig": {
    "respondType": "NOTIFY",
    "respondData": {
      "respondTemplate": "test single: [${SINGLE}]\ntest array: [${ARRAY}]\ntest bool: [${BOOL_SINGLE}]\ntest bool array:[${BOOL_ARRAY}]\ntest int:[${INT_SINGLE}]\ntest int array:[${INT_ARRAY}]",
      "notifyVariables": [
        {
          "variableName": "SINGLE",
          "jsonPath": "$.single",
          "replaceValue": {
            "Hi": "Hello!!"
          },
          "startFormat": "single column: %s%s"
        },
        {
          "variableName": "ARRAY",
          "jsonPath": "$.array[*].c1",
          "startFormat": "Start ->%s%s",
          "middleFormat": "%s->%s",
          "endFormat": "%s->%s->End"
        },
        {
          "variableName": "BOOL_SINGLE",
          "jsonPath": "$.bool",
          "startFormat": "single column: %s%s"
        },
        {
          "variableName": "BOOL_ARRAY",
          "jsonPath": "$.boolArray[*].c1",
          "startFormat": "Start ->%s%s",
          "middleFormat": "%s->%s",
          "endFormat": "%s->%s->End"
        },
        {
          "variableName": "INT_SINGLE",
          "jsonPath": "$.int",
          "startFormat": "single column: %s%s"
        },
        {
          "variableName": "INT_ARRAY",
          "jsonPath": "$.intArray[*].c1",
          "startFormat": "Start ->%s%s",
          "middleFormat": "%s->%s",
          "endFormat": "%s->%s->End"
        },
        {
          "variableName": "USER_LIST",
          "jsonPath": "$.array[*].user"
        },
        {
          "variableName": "GROUP_LIST",
          "jsonPath": "$.boolArray[*].group"
        }
      ]
    }
  }
}

```


Customize event(menu):
```
{
  "eventName": "TEST_MENU",
  "description": "Please enter %s for testing!",
  "variables": [
    {
      "displayName": "test data 1",
      "variableName": "TEST1"
    },
    {
      "displayName": "test data 2",
      "variableName": "TEST2"
    }
  ],
  "commConfig": {
    "methodType": "GET",
    "urlTemplate": "http://127.0.0.1:8080",
    "headerTemplate": "{\"Content-Type\": \"application/json\"}",
    "bodyTemplate": ""
  },
  "respondConfig": {
    "respondType": "MENU",
    "respondData": {
      "description": "Test menu",
      "displayNameTemplate": "id: ${C1}",
      "nextEvent": "TEST",
      "variables": [
        {
          "variableName": "C1",
          "jsonPath": "$.array[*].c1",
          "isGlobal": false
        },
        {
          "variableName": "C2",
          "jsonPath": "$.array[*].c2",
          "isGlobal": false
        },
        {
          "variableName": "SINGLE",
          "jsonPath": "$.single",
          "isGlobal": true,
          "replaceValue": {
            "Hi": "Hello! This is test menu!"
          }
        }
      ],
      "parameters": {
        "param1": "value1",
        "param2": "value2",
        "param3": "value3"
      }
    }
  }
}
```
### event-executor
#### TEST_NOTIFY
Communication Config:
```
{
  "methodType": "POST",
  "urlTemplate": "http://127.0.0.1:8080/${URL_END_POINT}",
  "headerTemplate": {
    "Content-Type": "application/json",
    "testHeader": "${TEST_HEADER}"
  },
  "bodyTemplate": {
    "testString": "${TEST_STRING}",
    "testBoolean": "${BOOL_TEST_BOOLEAN}",
    "testInteger": "${INT_TEST_INTEGER}"
  }
}
```
Respond config(Notify):
```
{
  "respondType": "NOTIFY",
  "respondData": {
    "respondTemplate": "test single: [${SINGLE}]\ntest array: [${ARRAY}]\ntest bool: [${BOOL_SINGLE}]\ntest bool array:[${BOOL_ARRAY}]\ntest int:[${INT_SINGLE}]\ntest int array:[${INT_ARRAY}]",
    "notifyVariables": [
      {
        "variableName": "SINGLE",
        "jsonPath": "$.single",
        "replaceValue": {
          "Hi": "Hello!!"
        },
        "startFormat": "single column: %s%s"
      },
      {
        "variableName": "ARRAY",
        "jsonPath": "$.array[*].c1",
        "startFormat": "Start ->%s%s",
        "middleFormat": "%s->%s",
        "endFormat": "%s->%s->End"
      },
      {
        "variableName": "BOOL_SINGLE",
        "jsonPath": "$.bool",
        "startFormat": "single column: %s%s"
      },
      {
        "variableName": "BOOL_ARRAY",
        "jsonPath": "$.boolArray[*].c1",
        "startFormat": "Start ->%s%s",
        "middleFormat": "%s->%s",
        "endFormat": "%s->%s->End"
      },
      {
        "variableName": "INT_SINGLE",
        "jsonPath": "$.int",
        "startFormat": "single column: %s%s"
      },
      {
        "variableName": "INT_ARRAY",
        "jsonPath": "$.intArray[*].c1",
        "startFormat": "Start ->%s%s",
        "middleFormat": "%s->%s",
        "endFormat": "%s->%s->End"
      },
      {
        "variableName": "USER_LIST",
        "jsonPath": "$.array[*].user"
      },
      {
        "variableName": "GROUP_LIST",
        "jsonPath": "$.boolArray[*].group"
      }
    ]
  }
}

```
Start execute event:
```
{
  "eventName": "TEST_NOTIFY",
  "executor": "OAO",
  "parameters": {
    "URL_END_POINT": "test",
    "TEST_HEADER": "Test header",
    "TEST_STRING": "Test String",
    "BOOL_TEST_BOOLEAN": true,
    "INT_TEST_INTEGER": 123456
  }
}
```
API Get Response json:
```
{
  "single": "Hi",
  "array": [
    {
      "c1": "11",
      "c2": "12",
      "user": "OMO"
    },
    {
      "c1": "21",
      "c2": "22",
      "user": "OVO"
    },
    {
      "c1": "31",
      "c2": "32",
      "user": "OWO"
    },
    {
      "c1": "41",
      "c2": "42",
      "user": "OUO"
    }
  ],
  "bool": false,
  "boolArray": [
    {
      "c1": true,
      "group": "group1"
    },
    {
      "c1": false,
      "group": "group2"
    }
  ],
  "int": 100,
  "intArray": [
    {
      "c1": "123"
    },
    {
      "c1": "456"
    }
  ]
}
```
Notify output:
```
{
  "usernameList": [
    "OAO",
    "OMO",
    "OVO",
    "OWO",
    "OUO"
  ],
  "message": "test single: [single column: Hello!!]\ntest array: [Start ->11->21->31->41->End]\ntest bool: [single column: false]\ntest bool array:[Start ->true->false->End]\ntest int:[single column: 100]\ntest int array:[Start ->123->456->End]"
}
```

#### TEST_MENU

Respond config(Menu):
```
{
  "respondType": "MENU",
  "respondData": {
  "description": "Test menu",
  "displayNameTemplate": "id: ${C1}",
  "nextEvent": "TEST",
  "variables": [
    {
      "variableName": "C1",
      "jsonPath": "$.array[*].c1",
      "isGlobal": false
    },
    {
      "variableName": "C2",
      "jsonPath": "$.array[*].c2",
      "isGlobal": false
    },
    {
      "variableName": "SINGLE",
      "jsonPath": "$.single",
      "isGlobal": true,
      "replaceValue": {
        "Hi": "Hello! This is test menu!"
      }
    }
  ],
  "parameters": {
    "param1": "value1",
    "param2": "value2",
    "param3": "value3"
  }
}
}
```


Start execute event:
```
{
  "eventName": "TEST",
  "executor": "OAO",
  "variables": {
    "userId": "12345",
    "userName": "john_doe"
  }
}
```
API Get Response json:
```
{
  "single": "Hi",
  "array": [
    {
      "c1": "11",
      "c2": "12"
    },
    {
      "c1": "21",
      "c2": "22"
    },
    {
      "c1": "31",
      "c2": "32"
    },
    {
      "c1": "41",
      "c2": "42"
    }
  ],
  "bool": false,
  "boolArray": [
    {
      "c1": true
    },
    {
      "c1": false
    }
  ],
  "int": 100,
  "intArray": [
    {
      "c1": "123"
    },
    {
      "c1": "456"
    }
  ]
}

```
Menu output:
```
{
  "eventName": "MENU",
  "username": "OAO",
  "description": "Test menu",
  "options": [
    {
      "displayName": "id: 11",
      "nextEvent": "TEST",
      "optionParameters": {
        "C1": "11",
        "C2": "12"
      }
    },
    {
      "displayName": "id: 21",
      "nextEvent": "TEST",
      "optionParameters": {
        "C1": "21",
        "C2": "22"
      }
    },
    {
      "displayName": "id: 31",
      "nextEvent": "TEST",
      "optionParameters": {
        "C1": "31",
        "C2": "32"
      }
    },
    {
      "displayName": "id: 41",
      "nextEvent": "TEST",
      "optionParameters": {
        "C1": "41",
        "C2": "42"
      }
    }
  ],
  "parameters": {
    "param1": "value1",
    "param2": "value2",
    "param3": "value3",
    "SINGLE": "Hello! This is test menu!"
  }
}
```
Notify output:
```
{
  "usernameList": [
    "OAO"
  ],
  "message": "test single: [single column: Hello!!], test array: [Start ->11->21->31->41->End]"
}
```
### device-database
User control event:
```
{
  "username": "xxx",
  "deviceId": "123456",
  "functionId": 0,
  "parameters": {
    "switch": "1"
  }
}
```

### device-connector





### group-manager
### line-service
### telegram-service

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






## IoT protocol
### hand shake
1. 裝置將透過api項系統要求一個Id給裝置，並給予mqtt broker host位置等訊息
3. 裝置透過info主題上傳自身基本資料，並訂閱自己的control主題
4. 握手完成，系統對裝置的訊息透過control主題，而裝置對系統的訊息透過state主題

### MQTT Topic:
所有裝置必須訂閱自己的control主題，info用於提供基本資料，state用於提供裝置狀態，具體格式參考下面範例。

1. devices/info
2. devices/state
3. devcies/{deviceId}/control


devices/info example:
```
{
  "deviceId": "123456",
  "deviceName": "Test device",
  "description": "This is a test device, make by ESP32!",
  "owner": "xxx",
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
      "stateType": "ACTIVE",
      "dataType": "ANY_FLOAT",
      "stateName": "temperture",
      "valueUnit": "°C"
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
    }
  ]
}

```

devices/state example:
```
{
  "executor": "OAO",
  "deviceId": "123456",
  "stateId": 0,
  "stateValue": 0
}

{
  "deviceId": "123456",
  "stateId": 1,
  "stateValue": 25.73
}
```
devcies/{deviceId}/control example:
```
{
  "deviceId": "{deviceId}",
  "executor": "xxx",
  "functionId": 0,
  "parameter": 0
}
```
### 安全性問題
我沒做，但下面提到的東東是能做到的。
info與state主題其他裝置並不可見，所以訊息中的驗證碼並不會被洩漏。
而任何的control主題只有系統能夠推送，所以就算id也不能被控制，但可能被監聽，所以裝置的key就很重要了，訊息本身會進行加密，被監聽到也無法解讀。
同時兩邊通訊再使用tls加密，完全保護安全性，如果需要更高安全性，裝置也可以定期重新生成金鑰，透過api更新。
api部分走https協定。
應該很安全，吧？
不能防ddos就是了
## IM protocol
### 接收訊息
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

### 傳送訊息
透過訂閱Rabbit MQ特定主題(IM-Integration-System.{IM平台名稱})，接收並能解析本系統給予的訊息格式，本系統訊息格式範例:
```
{
    "userIdList": ["M platform user id", "M platform user id", "M platform user id"],
    "message": "message to user"
}
```
再透過該IM平台之訊息寄送功能，將訊息寄送給使用者。

## IM User Event
### 事件類型


### 特殊變數名稱
| Variable Name  | Description    | Work On         | Remark                     |
|:-------------- |:-------------- |:--------------- |:-------------------------- |
| PLATFORM       | IM平台名稱     | 所有事件        | 在事件新增時自動加入       |
| USER_ID        | IM平台User id  | 所有事件        | 在事件新增時自動加入       |
| EVENT_NAME     | 事件名稱       | 所有事件        | 在事件新增時自動加入       |
| USERNAME       | 本系統username | 所有事件        | 在事件新增時自動加入       |
| USER_LIST      | username清單   | notify事件      | 加入此清單的user會收到通知 |
| GROUP_LIST     | group id清單   | notify事件      | 加入此清單的群組會收到通知 |
| NEXT_EVENT     | 指定下個事件   | menu事件        | 指定單獨選項的下個事件     |
<!-- |               |                |            |                            | -->

### 事件模板變數
任何以Template結尾之欄位皆為模板欄位
模板欄位可以使用特殊模板變數進行插值

#### String模板變數範例:
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

#### Integer模板變數範例:
```
{
    "IntegerTemplate":"${INT_MyInteger}"
}
```
> 其中"${INT_MyInteger}"的部分會被名為INT_MyInteger的變數值取代
> Integer模板變數必須以INT_開頭，否則會被視為String
> 只能單獨使用

假設INT_MyInteger的值為123456，則結果為:
```
{
    "IntegerTemplate":123456
}
```

#### Boolean模板變數範例:
```
{
    "BooleanTemplate":"${BOOL_MyBoolean}"
}
```
> 其中"${BOOL_MyBoolean}"的部分會被名為BOOL_MyBoolean的變數值取代
> Boolean模板變數必須以BOOL_開頭，否則會被視為String
> 只能單獨使用

假設BOOL_MyBoolean的值為true，則結果為:
```
{
    "BooleanTemplate": true
}
```

###### tags: `Microservice` `Java Spring` `Kubernetes` 
