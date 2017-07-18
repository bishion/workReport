BUI.use(['bui/form', 'bui/data', 'bui/grid'],
    function (Form, Data, Grid) {
        $.ajax({
            url: "user/getName.json",
            type: "post",
            async: false,
            success: function (data) {
                if (data.message) {
                    $("#welcome").text(data.message);

                } else {
                    $("#welcome").text("您还未登陆!");
                }
            }
        });
        $.ajax({
            url: "user/getWeekNo.json",
            type: "post",
            async: false,
            success: function (data) {
                if (data.message) {
                    $("#welcomeWeekNo").text(data.message);
                    var date = new Date();
                    $("#startDate").val(date.format("yyyy-MM-dd"));
                    $("#endDate").val(date.format("yyyy-MM-dd"));
                }
            }
        });
        var contextPath = "work/";
        var url_query = contextPath + 'query.json';
        var queryForm = new Form.HForm({
            srcNode: '#queryForm',
            autoRender: true
        });
        queryForm.render();
        var columns = [{
            title: "开发人员",
            dataIndex: "developer",
            width: '100'
        }, {
            title: "周报类型",
            dataIndex: "type",
            width: '100'
        }, {
            title: "任务类型",
            dataIndex: "workType",
            width: '100'
        }, {
            title: "项目名称",
            dataIndex: "projectName",
            width: '100'
        },{
            title: "任务进度(%)",
            dataIndex: "process",
            width: '100'
        }, {
            title: "任务状态",
            dataIndex: "status",
            width: '100'
        }, {
            title: "需求/问题任务",
            dataIndex: "workContent",
            width: '100%'
        }];

        var store = new Data.Store({
            proxy: {
                method: "POST",
                dataType: "json"
            },
            url: url_query,
            pageSize: 20,
            params: queryForm.toObject(),
            autoLoad: false
        });
        var grid = new Grid.Grid({
            render: '#grid',
            columns: columns,
            width: '100%',
            loadMask: true,
            store: store,
            emptyDataTpl: '<div class="centered"><img alt="Crying" src="custom/img/crying.png"><h2>数据不存在，请检查查询条件</h2></div>',
            bbar: {
                pagingBar: true
            },
            plugins: [Grid.Plugins.RowNumber],
            autoRender: true
        });


        $('#btnSearch').on('click', function () {
            store.load(queryForm.toObject());
        });
        $("#download").on("click",function () {
            queryForm.set("action","admin/download.json");
            queryForm.submit();
        })

    });