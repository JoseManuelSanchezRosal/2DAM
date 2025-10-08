function funcionclases(){
    let vector = document.getElementsByClassName("tparrafo");
    let i;

    for (i=0 ; i<vector.length ; i++){
        vector[i].innerHTML = i + "";
    }
    
}

function funcionetiqueta(){
    let vector = document.getElementsByTagName("p");
    let i;

    for (i=0 ; i<vector.length ; i++){
        vector[i].innerHTML = i + "";
    }
    
}