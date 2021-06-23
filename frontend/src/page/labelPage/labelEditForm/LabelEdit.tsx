import React from 'react';
import styled from 'styled-components';
import { LabelType } from 'components/common/tabModal/tapDataType';
import LabelBadge from 'components/atom/LabelBadge';
import InputField from 'components/atom/InputField';
import useInput from 'hooks/useInput';
import { ReactComponent as RefreshIcon } from 'assets/icon/RefreshIcon.svg';

interface inputDataType {
  defaultValue: string;
  onChange: ({ target }: { target: HTMLInputElement }) => void;
}

interface Props {
  label: LabelType;
  titleInput: inputDataType;
  descriptionInput: inputDataType;
  colorInput: inputDataType;
}

export default function LabelEdit({
  label: { name, color },
  titleInput,
  descriptionInput,
  colorInput,
}: Props) {
  return (
    <LabelEditBlock>
      <LabelBadge className='label__edit-badge' color={color} desc={name ? name : '레이블 이름'} />
      <div className='label__edit'>
        <InputField label='레이블 이름' {...titleInput} />
        <InputField label='설명(선택)' {...descriptionInput} />
        <div className='label__edit-color'>
          <div className='label__refresh'>
            <RefreshIcon />
          </div>
          <InputField className='label__color' label='배경색상' {...colorInput} />
        </div>
      </div>
    </LabelEditBlock>
  );
}

const LabelEditBlock = styled.div`
  display: grid;
  grid-template-columns: 10% 90%;
  align-items: center;
  margin-bottom: 1rem;
  .label__edit-badge {
    margin-bottom: 1rem;
    margin-left: 1.5rem;
  }
  .label__edit {
    display: flex;
    flex-direction: column;
    & > div {
      margin-bottom: 1rem;
    }
  }

  .label__edit-color {
    display: flex;
    align-items: center;
    .label__refresh {
      display: flex;
      align-items: center;
      justify-content: center;
      background-color: ${({ theme }) => theme.color.bgGrey};
      width: 53px;
      height: 53px;
      border-radius: 11px;
      margin-right: 8px;
    }
    .label__refresh:hover {
      background-color: ${({ theme }) => theme.color.inputBg};
    }
    .label__color {
      width: 240px;
    }
  }
`;
