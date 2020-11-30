import { FileManager } from "./file-manager.js";
import { IdToName } from "./id-to-name.js";

window.onload = function () {

    this.fileManager = new FileManager();

    retrieveInfoRequest();
};

var retrieveInfoRequest = function() {

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
                //logic to add attachment to table
                if (xhr.status === 200) {
                    let element = JSON.parse(xhr.responseText);
                    showInfo(element);
                    retrieveAttachments(element);
                    overrideResponse(element);
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
    var table = document.getElementById("view-request-table");
    var tableRow = document.createElement("tr");
    var idCol = document.createElement("td");
    var recipCol = document.createElement("td");
    var senderCol = document.createElement("td");
    var dateCol = document.createElement("td");
    var urgentCol = document.createElement("td");
    var description = document.getElementById("view-info-description");

    tableRow.appendChild(idCol);
    tableRow.appendChild(recipCol);
    tableRow.appendChild(senderCol);
    tableRow.appendChild(dateCol);
    tableRow.appendChild(urgentCol);
    table.childNodes[3].appendChild(tableRow);


    idCol.innerText = info.infoId;
    recipCol.innerText = IdToName(info.destinationId);
    senderCol.innerText = info.sender;
    urgentCol.innerText = info.urgent;
    dateCol.innerText = dateTimeFormat(info.dateTime);
    description.innerText = info.description;

}

var retrieveAttachments = function(info) {

    let xhr = new XMLHttpRequest();
    let url = "http://137.116.126.93/manager/view-reimbursement/" + info.relatedId + "/attachments";
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
    var fileCol = document.createElement("td");
    var downloadCol = document.createElement("td");

    tableRow.appendChild(fileCol);
    tableRow.appendChild(downloadCol);
    table.childNodes[3].appendChild(tableRow);

    fileCol.innerHTML = attachment.fileType;
    downloadCol.innerHTML = "<button id=\"download-attachment-btn\" type=\"button\" class=\"btn table-btn\">Download</button>";
    downloadCol.firstChild.addEventListener("click", (event) => {
        let manager = new FileManager();
        manager.retrieveDownload(attachment);
    });
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

var overrideResponse = function(info) {
    let form = document.getElementById("info-response-form");
    form.action = info.infoId + "/response";
}