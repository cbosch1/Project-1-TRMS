window.onload = function () {

    var xhr = new XMLHttpRequest();
    var url = window.location.origin + "/my-reimbursements";
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
                    let runningTotal = 1000;
                    reimburseList.forEach(function (element) {
                        if(element.status == "APPROVED" || element.status == "PENDING"){
                            let jsonDate = element.dateTime;
                            let d = new Date(jsonDate.year +"-"+ jsonDate.monthValue +"-"+ jsonDate.dayOfMonth);
                            let date = new Date(Date.now());

                            date.setFullYear(date.getFullYear() - 1);

                            if(d > (date)) {
                                if(runningTotal - element.projected > 0) {
                                    runningTotal -= element.projected;
                                } else {
                                    runningTotal = 0;
                                }
                            }
                        }
                        let used = document.getElementById("user-fund-used");
                        let avail = document.getElementById("user-fund-available");
                        let total = document.getElementById("user-fund-total");
                        used.innerHTML = "$" + (1000 - runningTotal);
                        avail.innerHTML = "$" + runningTotal;
                        total.innerHTML = "$" + ((1000 - runningTotal) + runningTotal);
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
