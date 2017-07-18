BUI.use(['bui/form', 'bui/data', 'bui/grid'],
    function (Form, Data, Grid) {
        $.ajax({
            url: "user/getName.json",
            type: "post",
            async: false,
            success: function (data) {
                if (data.message) {
                    $("#queryForm input[name='reporter']").val(data.message);
                } else {
                    $("#welcome").text("您还未登陆!");
                }
            }
        });
        var contextPath = "bugReport/";
        var url_query = contextPath + 'query.json';
        var url_update = contextPath + 'update.json';
        var url_insert = contextPath + 'insert.json';


        var queryForm = new Form.HForm({
            srcNode: '#queryForm',
            autoRender: true
        });

        var columns = [{
            title: "登记人",
            dataIndex: "reporter",
            width: '100'
        }, {
            title: "问题类型",
            dataIndex: "type",
            width: '100'
        }, {
            title: "所属菜单",
            dataIndex: "menu",
            width: '150'
        }, {
            title: "问题描述",
            dataIndex: "description",
            width: '100%'
        }, {
            title: "处理状态",
            dataIndex: "status",
            width: '100'
        }, {
            title: "登记时间",
            dataIndex: "createDate",
            width: '100'
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
            plugins: [Grid.Plugins.RadioSelection],
            autoRender: true
        });

        var codeForm = new Form.HForm({
            srcNode: '#codeForm'
        });
        codeForm.render();

        grid.on('itemclick', function (ev) {
            codeForm.enable();
            var item = ev.item;
            codeForm.setRecord(item);
            codeForm.disable();
        });

        $('#btnSearch').on('click', function () {
            store.load(queryForm.toObject());
        });
        function enableBtnEdit() {
            $('#btnSave, #btnReset').attr('disabled', false);
        }

        function disableBtnEdit() {
            $('#btnSave, #btnReset').attr('disabled', true);
        }

        codeForm.disable();
        disableBtnEdit();
        var operation = '';

        $('#add').on('click', function () {

            operation = 'add';
            codeForm.enable();
            enableBtnEdit();

            codeForm.clearFields();
            codeForm.valid();
        });

        $('#edit').on('click', function () {
            var item = grid.getSelected();

            if (item == null) {
                BUI.Message.Alert('请选择记录', 'error');
                return;
            }

            operation = 'edit';
            enableBtnEdit();
            codeForm.enable();
        });

        $('#btnReset').on('click', function () {
            if (operation === 'edit') {
                var item = grid.getSelected();
                codeForm.setRecord(item);
            } else if (operation === 'add') {
                codeForm.clearFields();
            }
        });

        $('#btnSave').on('click', function () {
            if (!codeForm.isValid()) {
                return;
            }

            var data = codeForm.toObject();
            disableBtnEdit();
            codeForm.disable();
            if (operation === 'add') {
                $.ajax({
                    url: url_insert,
                    type: 'post',
                    data: data,
                    success: function (data) {
                        if (data.success) {
                            BUI.Message.Alert('添加成功', 'success');
                            store.load();
                        } else {
                            BUI.Message.Alert(data.message, 'error');
                            enableBtnEdit();
                            codeForm.enable();
                        }
                    }
                });

            } else if (operation === 'edit') {
                var item = grid.getSelected();
                data.id = item.id;
                $.ajax({
                    url: url_update,
                    type: 'post',
                    data: data,
                    success: function (data) {
                        if (data.success) {
                            BUI.Message.Alert('修改成功', 'success');
                            store.load();
                        } else {
                            BUI.Message.Alert(data.message, 'error');
                            enableBtnEdit();
                            codeForm.enable();
                        }
                    },
                    error:function (data) {
                        console.log(data)
                    }
                })
            }
        });

    });