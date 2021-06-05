
$(window).bind('keydown', function (event) {
    if(event.ctrlKey || event.metaKey){
        var charCode = String.fromCharCode(event.which).toLowerCase()
        if (charCode === "s"){
            event.preventDefault();
            var detailNote = {};
            detailNote["noteDetail"] = document.getElementById("editNote").innerHTML;
            detailNote["id"] = findGetParameter("noteId");

            console.log(detailNote);
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/notes/details",
                data: JSON.stringify(detailNote),
                dataType: 'json',
                cache: false,
                timeout: 600000,
                success: function (data) {

                    console.log("SUCCESS : ", data);
                },
                error: function (e) {
                    console.log("ERROR : ", e);

                }
            });
        }
    }
})

function findGetParameter(parameterName) {
    var result = null,
        tmp = [];
    location.search
        .substr(1)
        .split("&")
        .forEach(function (item) {
            tmp = item.split("=");
            if (tmp[0] === parameterName) result = decodeURIComponent(tmp[1]);
        });
    return result;
}