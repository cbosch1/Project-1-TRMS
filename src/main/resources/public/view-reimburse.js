window.onload = function () {

    let requestRetrieved = false;

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
};

var showRequest = function (reimburse) {
    var table = document.getElementById("view-request-table");
    var tableRow = document.createElement("tr");
    var idCol = document.createElement("td");
    var dateCol = document.createElement("td");
    var typeCol = document.createElement("td");
    var locationCol = document.createElement("td");
    var costCol = document.createElement("td");
    var payoutCol = document.createElement("td");
    var statusCol = document.createElement("td");
    var urgentCol = document.createElement("td");
    var cancelCol = document.createElement("td");
    var descriptionArea = document.getElementById("view-description");
    var justificationArea = document.getElementById("view-justification");
    var gradingArea = document.getElementById("view-grading");

    tableRow.appendChild(idCol);
    tableRow.appendChild(dateCol);
    tableRow.appendChild(typeCol);
    tableRow.appendChild(locationCol);
    tableRow.appendChild(costCol);
    tableRow.appendChild(payoutCol);
    tableRow.appendChild(statusCol);
    tableRow.appendChild(urgentCol);
    tableRow.appendChild(cancelCol);
    table.childNodes[3].appendChild(tableRow);

    idCol.innerHTML = reimburse.requestId;
    dateCol.innerHTML = reimburse.dateTime.monthValue +"-"+ reimburse.dateTime.dayOfMonth +"-"+ reimburse.dateTime.year;
    typeCol.innerHTML = reimburse.type;
    locationCol.innerHTML = reimburse.location;
    costCol.innerHTML = "$"+ reimburse.cost;
    payoutCol.innerHTML = "$"+ reimburse.projected;
    statusCol.innerHTML = reimburse.status;
    urgentCol.innerHTML = reimburse.urgent;
    descriptionArea.innerHTML = reimburse.description;
    justificationArea.innerHTML = reimburse.justification;
    gradingArea.innerHTML = reimburse.grading;

    if (reimburse.status == "PENDING"){
        cancelCol.innerHTML = "<form id=\"view-reimburse-form\" method=\"POST\" action=\"../cancel-reimbursement/" + reimburse.requestId + "\">\n                            <button id=\"view-reimburse-btn\" type=\"submit\" class=\"btn table-btn\">Cancel</button>\n                        </form>";
        cancelCol.addEventListener("click", function (event) {
            event.preventDefault();
            event.stopPropagation();
            console.log("clicked");
            if(window.confirm("Are you sure you want to delete this request? This cannot be undone.")){
                console.log(this.firstChild);
                this.firstChild.submit();
            }
        });
    }
};

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
    downloadCol.innerHTML = "<form id=\"download-attachment-form\" method=\"GET\" action=\"../download-attachment/" + attachment.attachId + "\">\n                            <button id=\"download-attachment-btn\" type=\"submit\" class=\"btn table-btn\">Download</button>\n                        </form>";
}
