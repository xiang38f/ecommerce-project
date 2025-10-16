# 簡易電商購物中心系統 - 玉山銀行 Java 工程師實作題

您好，感謝您的審閱。

本專案是根據後端工程師職位的實作題要求所開發的一個簡易電商平台。專案採用前後端分離架構，並遵循題目所要求的技術規範進行實作。

## 核心功能

* **後台管理**：
    * **新增商品**：管理人員可以新增商品，包含商品名稱、售價與庫存數量。 
* **前台購物**：
    * **商品瀏覽**：顧客可以瀏覽所有庫存大於零的商品清單。 
    * **建立訂單**：顧客可勾選多項商品、設定購買數量，並填寫顧客資訊以建立訂單。訂單成功建立後，系統會自動更新對應的商品庫存。 
* **訂單查詢**：
    * **訂單列表**：查看系統中所有訂單的摘要紀錄。
    * **訂單詳情**：查詢單一筆訂單的詳細購買品項。

## 系統架構

專案採用標準的**三層式架構 (Three-Tier Architecture)** 進行開發，包含 Web Server、Application Server 以及 Database Server。 
後端程式碼結構亦劃分為展示層 (Controller)、業務層 (Service)、資料層 (Repository) 以及共用層 (DTO, Config 等)。 

## 環境設定與啟動指南

請依照以下步驟設定與啟動本專案。

### 1. 資料庫設定

1.  執行位於 `/DB` 資料夾下的 `DDL.sql` 檔案。此腳本將會建立所有必要的資料表結構以及預存程序。
2.  執行 `/DB` 資料夾下的 `DML.sql` 檔案。此腳本將會寫入題目要求的初始商品資料。
3.  修改後端專案 `backend/src/main/resources/application.properties`檔案中的資料庫連線設定 (URL, username, password)，使其指向您剛建立的資料庫。

### 2. 後端 (Backend) 啟動

1.  使用 IntelliJ IDEA 或 Eclipse 等 IDE 開啟位於 `/backend` 的 Spring Boot 專案。
2.  等待 Maven 自動下載所有依賴。
3.  執行專案的主應用程式類別 (OrderSystemDemoApplication.java)。
4.  後端服務預設將運行在 `http://localhost:8080`。

### 3. 前端 (Frontend) 啟動

1.  使用終端機 (Terminal) 進入 `/frontend` 資料夾。
2.  執行 `npm install` 來安裝所有前端依賴套件。
3.  執行 `npm run dev` 來啟動前端開發伺服器。
4.  前端應用程式預設將運行在 `http://localhost:5173`。
5.  透過瀏覽器輸入 `http://localhost:5173` 來使用本系統。


感謝您的時間！

