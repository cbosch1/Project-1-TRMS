window.onload = function () {
    this.userInfo; 
    getMyInfo();
};

function getMyInfo() {
    var xhr = new XMLHttpRequest();
    var url = "http://52.149.146.226/manager/myinfo";
    var user;
    //sets up ready state handler
    xhr.onreadystatechange = function () {
        console.log(xhr.readyState);
        switch (xhr.readyState) {
            case 0:
                console.log("Nothing, initialized not sent");
                break;
            case 1:
                console.log("Connection established");
                break;
            case 2:
                console.log("Request sent");
                break;
            case 3:
                console.log("Waiting response");
                break;
            case 4:
                console.log("Response received");
                //logic to add requests to table
                if (xhr.status === 200) {
                    window.userInfo = JSON.parse(xhr.responseText);
                    this.setViewTables();
                    this.setWelcome();
                }
                break;
        }
    };
    //opens up the request
    xhr.open("POST", url, true);
    //sends request
    xhr.send();
}

function setWelcome(user) {
    let welcome = document.getElementById("welcome");
    welcome.innerHTML = "Welcome " + window.userInfo.username;
}

function setViewTables() {
    var xhr = new XMLHttpRequest();
    var url = "http://52.149.146.226/manager/requests";
    //sets up ready state handler
    xhr.onreadystatechange = function () {
        console.log(xhr.readyState);
        switch (xhr.readyState) {
            case 0:
                console.log("Nothing, initialized not sent");
                break;
            case 1:
                console.log("Connection established");
                break;
            case 2:
                console.log("Request sent");
                break;
            case 3:
                console.log("Waiting response");
                break;
            case 4:
                console.log("Response received");
                //logic to add requests to table
                if (xhr.status === 200) {
                    let reimburseList = JSON.parse(xhr.responseText);
                    reimburseList.forEach(function (element) {
                        if(element.stage == window.userInfo.privilege){
                            this.addRowNeed(element);
                        } else {
                            this.addRowOther(element);
                        }
                    });
                }
                break;
        }
    };
    //opens up the request
    xhr.open("GET", url, true);
    //sends request
    xhr.send();
}

function addRowNeed(reimburse) {
    var table = document.getElementById("pending-requests-table");
    var tableRow = document.createElement("tr");
    var empCol = document.createElement("td");
    var dateCol = document.createElement("td");
    var typeCol = document.createElement("td");
    var locationCol = document.createElement("td");
    var stageCol = document.createElement("td");
    var urgentCol = document.createElement("td");
    var payoutCol = document.createElement("td");
    var viewCol = document.createElement("td");

    tableRow.appendChild(empCol);
    tableRow.appendChild(dateCol);
    tableRow.appendChild(typeCol);
    tableRow.appendChild(locationCol);
    tableRow.appendChild(stageCol);
    tableRow.appendChild(urgentCol);
    tableRow.appendChild(payoutCol);
    tableRow.appendChild(viewCol);
    table.childNodes[3].appendChild(tableRow);

    empCol.innerHTML = reimburse.employeeId;
    dateCol.innerHTML = reimburse.dateTime.monthValue +"-"+ reimburse.dateTime.dayOfMonth +"-"+ reimburse.dateTime.year;
    typeCol.innerHTML = reimburse.type;
    locationCol.innerHTML = reimburse.location;
    stageCol.innerHTML = reimburse.stage;
    urgentCol.innerHTML = reimburse.urgent;
    payoutCol.innerHTML = "$"+ reimburse.projected;
    viewCol.innerHTML = "<form id=\"view-reimburse-form\" method=\"GET\" action=\"manager/view-reimbursement/" + reimburse.requestId + "\">\n<button id=\"view-reimburse-btn\" type=\"submit\" class=\"btn table-btn\">View</button>\n</form>";
};

function addRowOther(reimburse) {
    var table = document.getElementById("other-requests-table");
    var tableRow = document.createElement("tr");
    var empCol = document.createElement("td");
    var dateCol = document.createElement("td");
    var typeCol = document.createElement("td");
    var locationCol = document.createElement("td");
    var stageCol = document.createElement("td");
    var urgentCol = document.createElement("td");
    var payoutCol = document.createElement("td");
    var viewCol = document.createElement("td");

    tableRow.appendChild(empCol);
    tableRow.appendChild(dateCol);
    tableRow.appendChild(typeCol);
    tableRow.appendChild(locationCol);
    tableRow.appendChild(stageCol);
    tableRow.appendChild(urgentCol);
    tableRow.appendChild(payoutCol);
    tableRow.appendChild(viewCol);
    table.childNodes[3].appendChild(tableRow);

    empCol.innerHTML = reimburse.employeeId;
    dateCol.innerHTML = reimburse.dateTime.monthValue +"-"+ reimburse.dateTime.dayOfMonth +"-"+ reimburse.dateTime.year;
    typeCol.innerHTML = reimburse.type;
    locationCol.innerHTML = reimburse.location;
    stageCol.innerHTML = reimburse.stage;
    urgentCol.innerHTML = reimburse.urgent;
    payoutCol.innerHTML = "$"+ reimburse.projected;
    viewCol.innerHTML = "<form id=\"view-reimburse-form\" method=\"GET\" action=\"manager/view-reimbursement/" + reimburse.requestId + "\">\n<button id=\"view-reimburse-btn\" type=\"submit\" class=\"btn table-btn\">View</button>\n</form>";
};
