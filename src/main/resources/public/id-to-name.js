let IdToName = function (id) {
    let name = "";

    var xhr = new XMLHttpRequest();
    var url = "http://52.149.146.226/employee/" + id;
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
                    let employee = JSON.parse(xhr.responseText);
                    name = employee;
                }
                break;
        }
    };
    //opens up the request
    xhr.open("GET", url, true);
    //sends request
    xhr.send();

    return name;
}

export {IdToName};