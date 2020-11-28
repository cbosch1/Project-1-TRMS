
window.onload = function() {
    let xhr = new XMLHttpRequest();
    const url = "http://localhost:2839/my-reimbursements";
    //sets up ready state handler
    xhr.onreadystatechange = function () {
        console.log(xhr.readyState);
        switch (xhr.readyState) {

            case 0:
                console.log("Nothing, initialized not sent");
                break;
            case 1:
                console.log("Connection established")
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
                    console.log(reimburseList[0]);
                    reimburseList.forEach(element => {
                        addRow(element);
                    });
                }
                break;

        }
    }

    //opens up the request
    xhr.open("GET", url, true);
    //sends request
    xhr.send();

}

let addRow = function (reimburse) {
    
    let table = document.getElementById("current-requests-table");
    let tableRow = document.createElement("tr");
    let idCol = document.createElement("td");
    let dateCol = document.createElement("td");
    let typeCol = document.createElement("td");
    let locationCol = document.createElement("td");
    let costCol = document.createElement("td");
    let payoutCol = document.createElement("td");
    let statusCol = document.createElement("td");
    let viewCol = document.createElement("td");


    tableRow.appendChild(idCol);
    tableRow.appendChild(dateCol);
    tableRow.appendChild(typeCol);
    tableRow.appendChild(locationCol);
    tableRow.appendChild(costCol);
    tableRow.appendChild(payoutCol);
    tableRow.appendChild(statusCol);
    tableRow.appendChild(viewCol);

    table.appendChild(tableRow);

    idCol.innerHTML = reimburse.requestId;
    dateCol.innerHTML = reimburse.dateTime;
    typeCol.innerHTML = reimburse.type;
    locationCol.innerHTML = reimburse.location;
    costCol.innerHTML = reimburse.cost;
    payoutCol.innerHTML = reimburse.projected;
    statusCol.innerHTML = reimburse.status;
    viewCol.innerHTML = `<form id="view-reimburse-form" method="GET" action="employee/view-reimbursement/${reimburse.requestId}">
                            <button id="view-reimburse-btn" type="submit" class="btn table-btn">View</button>
                        </form>`;

}