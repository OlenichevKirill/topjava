const mealAjaxUrl = "user/meals/";

const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        filterMeals();
    }
};

$(function () {
    makeEditable($("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});


function filterMeals() {
    $.get(ctx.ajaxUrl + "filter", $('#filter').serialize(), function (data) {
        updateDataTable(data)
    });
}

function clearFilter() {
    $('#filter').find(":input").val("");
    $.get(ctx.ajaxUrl, function (data) {
        updateDataTable(data);
    });
}