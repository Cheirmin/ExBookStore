 $('#keyword').keydown(function (e) {
     var q = $('#keyword').val();
     var html=[];
        $.ajax({
            type: "post",
            url: "/loadhot",
            data:{"hot":q},
            success:function (data) {
                $.each(data,function (index,value) {
                    console.log(data)
                    html[index]=value.configName;
                });
                $("#keyword").autocomplete({
                    source:html
                });
            }
        });
        
        var key = e.which; //e.which是按键的值
        // if (key == 13) {
        //     var q = $(this).val();
        //     if (q && q != '') {
        //         window.location.href = '/search?keyword=' + q;
        //     }
        // }
    });

function search() {
    var q = $('#keyword').val();
    // $.ajax({
    //     type:"post",
    //     url:"",
    // });
    // if (q && q != '') {
    //     window.location.href = '/search?keyword=' + q;
    // }
}