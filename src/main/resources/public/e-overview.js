window.onload = function () {
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:2839/my-reimbursements";
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
                        if(element.status == "PENDING"){
                            addRowCurrent(element);
                        } else {
                            addRowPast(element);
                        }
                    });
                }
                break;
        }
    };
    //opens up the request
    xhr.open("POST", url, true);
    //sends request
    xhr.send();
};

var addRowCurrent = function (reimburse) {
    var table = document.getElementById("current-requests-table");
    var tableRow = document.createElement("tr");
    var idCol = document.createElement("td");
    var dateCol = document.createElement("td");
    var typeCol = document.createElement("td");
    var locationCol = document.createElement("td");
    var costCol = document.createElement("td");
    var payoutCol = document.createElement("td");
    var statusCol = document.createElement("td");
    var viewCol = document.createElement("td");

    tableRow.appendChild(idCol);
    tableRow.appendChild(dateCol);
    tableRow.appendChild(typeCol);
    tableRow.appendChild(locationCol);
    tableRow.appendChild(costCol);
    tableRow.appendChild(payoutCol);
    tableRow.appendChild(statusCol);
    tableRow.appendChild(viewCol);
    table.childNodes[3].appendChild(tableRow);

    idCol.innerHTML = reimburse.requestId;
    dateCol.innerHTML = "" + reimburse.dateTime.monthValue +"-"+ reimburse.dateTime.dayOfMonth +"-"+ reimburse.dateTime.year;
    typeCol.innerHTML = reimburse.type;
    locationCol.innerHTML = reimburse.location;
    costCol.innerHTML = reimburse.cost;
    payoutCol.innerHTML = reimburse.projected;
    statusCol.innerHTML = reimburse.status;
    viewCol.innerHTML = "<form id=\"view-reimburse-form\" method=\"GET\" action=\"employee/view-reimbursement/" + reimburse.requestId + "\">\n                            <button id=\"view-reimburse-btn\" type=\"submit\" class=\"btn table-btn\">View</button>\n                        </form>";
};

var addRowPast = function (reimburse) {
    var table = document.getElementById("past-requests-table");
    var tableRow = document.createElement("tr");
    var idCol = document.createElement("td");
    var dateCol = document.createElement("td");
    var typeCol = document.createElement("td");
    var locationCol = document.createElement("td");
    var costCol = document.createElement("td");
    var payoutCol = document.createElement("td");
    var statusCol = document.createElement("td");
    var viewCol = document.createElement("td");

    tableRow.appendChild(idCol);
    tableRow.appendChild(dateCol);
    tableRow.appendChild(typeCol);
    tableRow.appendChild(locationCol);
    tableRow.appendChild(costCol);
    tableRow.appendChild(payoutCol);
    tableRow.appendChild(statusCol);
    tableRow.appendChild(viewCol);
    table.childNodes[3].appendChild(tableRow);

    idCol.innerHTML = reimburse.requestId;
    dateCol.innerHTML = "" + reimburse.dateTime.monthValue +"-"+ reimburse.dateTime.dayOfMonth +"-"+ reimburse.dateTime.year;
    typeCol.innerHTML = reimburse.type;
    locationCol.innerHTML = reimburse.location;
    costCol.innerHTML = reimburse.cost;
    payoutCol.innerHTML = reimburse.projected;
    statusCol.innerHTML = reimburse.status;
    viewCol.innerHTML = "<form id=\"view-reimburse-form\" method=\"GET\" action=\"employee/view-reimbursement/" + reimburse.requestId + "\">\n                            <button id=\"view-reimburse-btn\" type=\"submit\" class=\"btn table-btn\">View</button>\n                        </form>";
};
