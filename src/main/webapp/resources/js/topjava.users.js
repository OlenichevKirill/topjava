const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl,
    updateTable: function () {
        $.get(ctx.ajaxUrl, function (data) {
            updateDataTable(data);
        });
    }
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
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
                    "asc"
                ]
            ]
        })
    );
});

function enable(enable, userId) {
    let enableVal = enable.is(':checked');
    console.log(enableVal);
    $.post("rest/" + userAjaxUrl + userId, {enable: enableVal}, function () {
        enableVal ? enable.prop('checked', true) : enable.prop('checked', false);
        enableVal ? enable.closest('tr').attr("style", "") : enable.closest('tr').attr("style", "color: gray");
    })
}