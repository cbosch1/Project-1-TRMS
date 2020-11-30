import { FileManager } from "./file-manager.js";

window.onload = function () {
    window.userInfo; 
    getMyInfo();

    let xhr = new XMLHttpRequest();
    let url = window.location.pathname;
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
                    showRequest(JSON.parse(xhr.responseText));
                    retrieveAttachments();
                } else {    
                    let error = document.getElementById("login-error");
                    error.innerText = "Unauthorized attempt to view request";
                    error.style = "visibility: visible;";
                }
                break;
        }
    };
    //opens up the request
    xhr.open("POST", url, true);
    //sends request
    xhr.send();

    retrieveInfos();

    addRequestLink();
};


function addRequestLink() {
    
}

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
                    setWelcome();
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

var showRequest = function (reimburse) {
    var table = document.getElementById("review-request-table");
    var tableRow = document.createElement("tr");
    var idCol = document.createElement("td");
    var dateCol = document.createElement("td");
    var typeCol = document.createElement("td");
    var locationCol = document.createElement("td");
    var stageCol = document.createElement("td");
    var statusCol = document.createElement("td");
    var urgentCol = document.createElement("td");
    var costCol = document.createElement("td");
    var payoutCol = document.createElement("td");
    var descriptionArea = document.getElementById("view-description");
    var justificationArea = document.getElementById("view-justification");
    var gradingArea = document.getElementById("view-grading");
    var approvalArea = document.getElementById("approval-area");

    tableRow.appendChild(idCol);
    tableRow.appendChild(dateCol);
    tableRow.appendChild(typeCol);
    tableRow.appendChild(locationCol);
    tableRow.appendChild(stageCol);
    tableRow.appendChild(statusCol);
    tableRow.appendChild(urgentCol);
    tableRow.appendChild(costCol);
    tableRow.appendChild(payoutCol);
    table.childNodes[3].appendChild(tableRow);

    idCol.innerHTML = reimburse.employeeId;
    dateCol.innerHTML = reimburse.dateTime.monthValue +"-"+ reimburse.dateTime.dayOfMonth +"-"+ reimburse.dateTime.year;
    typeCol.innerHTML = reimburse.type;
    locationCol.innerHTML = reimburse.location;
    stageCol.innerHTML = reimburse.stage;
    statusCol.innerHTML = reimburse.status;
    urgentCol.innerHTML = reimburse.urgent;
    costCol.innerHTML = "$"+ reimburse.cost;
    payoutCol.innerHTML = "$"+ reimburse.projected;
    descriptionArea.innerHTML = reimburse.description;
    justificationArea.innerHTML = reimburse.justification;
    gradingArea.innerHTML = reimburse.grading;

    if (reimburse.stage == window.userInfo.privilege) {
        approvalArea.innerHTML = "<button id=\"deny-btn\" type=\"button\" class=\"login-table col-md-3\">Deny</button>"
                                +"<span class=\"col-md-6\"></span>"
                                +"<button id=\"approve-btn\" type=\"button\" class=\"login-table col-md-3\">Approve</button>";
    let denyBtn = document.getElementById("deny-btn");
    let approveBtn = document.getElementById("approve-btn");

    denyBtn.addEventListener("click", function() {reviewReimbursement("false")});

    approveBtn.addEventListener("click", function() {reviewReimbursement("true")});
    }
};

function reviewReimbursement(approval) {

    let xhr = new XMLHttpRequest();
    let url = window.location.pathname+"/"+approval;
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
                //logic to add attachment to table
                if (xhr.status === 200) {
                    var approvalArea = document.getElementById("approval-area");
                    approvalArea.innerHTML = "Review successfully completed";
                } else {
                    var errorArea = document.ge
                }
                break;
        }
    };
    //opens up the request
    xhr.open("PUT", url, true);
    //sends request
    xhr.send();
}

var retrieveAttachments = function() {

    let xhr = new XMLHttpRequest();
    let url = window.location.pathname+"/attachments";
    console.log(url);
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
                //logic to add attachment to table
                if (xhr.status === 200) {
                    let attachList = JSON.parse(xhr.responseText);
                    attachList.forEach(function (element) {
                        showAttachment(element);
                    });
                }
                break;
        }
    };
    //opens up the request
    xhr.open("POST", url, true);
    //sends request
    xhr.send();
}

var showAttachment = function(attachment) {
    var table = document.getElementById("view-attachments-table");
    var tableRow = document.createElement("tr");
    var idCol = document.createElement("td");
    var fileCol = document.createElement("td");
    var downloadCol = document.createElement("td");

    tableRow.appendChild(idCol);
    tableRow.appendChild(fileCol);
    tableRow.appendChild(downloadCol);
    table.childNodes[3].appendChild(tableRow);

    idCol.innerHTML = attachment.attachId;
    fileCol.innerHTML = attachment.fileType;
    downloadCol.innerHTML = "<button id=\"download-attachment-btn\" type=\"button\" class=\"btn table-btn\">Download</button>";
    downloadCol.firstChild.addEventListener("click", (event) => {
        let manager = new FileManager();
        manager.retrieveDownload(attachment);
    });
}

var retrieveInfos = function() {

    let xhr = new XMLHttpRequest();
    let url = window.location.pathname+"/infos";
    console.log(url);
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
                //logic to add attachment to table
                if (xhr.status === 200) {
                    let infoList = JSON.parse(xhr.responseText);
                    infoList.forEach(function (element) {
                        showInfo(element);
                    });
                }
                break;
        }
    };
    //opens up the request
    xhr.open("POST", url, true);
    //sends request
    xhr.send();
}

var showInfo = function(info) {
    var table = document.getElementById("information-requests");
    var tableRow = document.createElement("tr");
    var dateCol = document.createElement("td");
    var senderCol = document.createElement("td");
    var viewCol = document.createElement("td");

    tableRow.appendChild(dateCol);
    tableRow.appendChild(senderCol);
    tableRow.appendChild(viewCol);
    table.childNodes[3].appendChild(tableRow);

    dateCol.innerText = dateTimeFormat(info.dateTime);
    senderCol.innerText = info.sender;
    viewCol.innerHTML = "<form id=\"view-info-form\" method=\"GET\" action=\"../view-info/" + info.infoId + "\">\n                            <button id=\"view-info-btn\" type=\"submit\" class=\"btn table-btn\">View</button>\n                        </form>";
}

var dateTimeFormat = function(dateTime) {

    let date = dateTime.monthValue +"-"+ dateTime.dayOfMonth +"-"+ dateTime.year;
    let time = "";
    let afternoon = false; 
    
    if (dateTime.hour > 12) {
        time += (dateTime.hour - 12);
        afternoon = true;
    } else {
        time += dateTime.hour;
    }

    if (dateTime.minute > 9) {
        time += ":" + dateTime.minute;
    } else {
        time += ":" + "0" + dateTime.minute;
    }

    if (afternoon) {
        time += " PM";
    } else {
        time += " AM"
    }

    return (date + " " + time);
}