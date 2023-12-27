import { Modal } from 'antd';
import React, {useEffect, useRef} from 'react';
import {
    ProColumns,
    ModalForm,
    ProTable,
    ActionType
  } from '@ant-design/pro-components';

interface Props {
    columns: ProColumns<API.RuleListItem>[],
    openCreateModal: boolean,
    formSubmit: (values: any) => void,
    values: API.ApiInfoVO[],
}

const CreateFormModel: React.FC<Props> = (props) => {
    const updateForm = useRef<ActionType>();
    useEffect(() => {
       updateForm.current?.setFieldsValue?.(props.values);
    })
    
    return (<>
      <ModalForm
        title={"编辑接口信息"}
        width="500px"
        submitter={false}
        open={props.openCreateModal}
      >
      <ProTable
        type="form"
        actionRef={updateForm}
        columns={props.columns}
        onSubmit={(values) => props.formSubmit(values)}
      >
      </ProTable>

      </ModalForm>
    
    </>)
}

export default CreateFormModel