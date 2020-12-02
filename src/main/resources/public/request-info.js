import { FileManager } from "./file-manager.js";
import { IdToName } from "./id-to-name.js";

window.onload = function () {

    this.fileManager = new FileManager();

    retrieveInfoRequest();
};

var retrieveInfoRequest = function() {

    let xhr = new XMLHttpRequest();
    let url = window.location.pathname.slice(0, -12);
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
                    retrieveAttachments(element);
                }
                break;
        }
    };
    //opens up the request
    xhr.open("POST", url, true);
    //sends request
    xhr.send();
}

var retrieveAttachments = function(request) {

    let xhr = new XMLHttpRequest();
    let url = window.location.origin + "manager/view-reimbursement/" + request.requestId + "/attachments";
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