import { Modal } from 'antd';
import React from 'react';
import {
    ProColumns,
    ModalForm,
    ProTable,
  } from '@ant-design/pro-components';

interface Props {
    columns: ProColumns<API.RuleListItem>[],
    openCreateModal: boolean,
    formSubmit: (values: any) => void,
}

const CreateFormModel: React.FC<Props> = (props) => {
    return (<>
        <ModalForm
        title={"新增接口信息"}
        width="500px"
        submitter={false}
   
        open={props.openCreateModal}
      >
      <ProTable
        type="form"
        columns={props.columns}
        onSubmit={(values) => props.formSubmit(values)}
      >
      </ProTable>

      </ModalForm>
    
    </>)
}

export default CreateFormModel