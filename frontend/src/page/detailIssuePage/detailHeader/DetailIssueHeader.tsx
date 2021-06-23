import React, { useEffect } from 'react';
import styled from 'styled-components';
import { useRecoilValue, useRecoilState } from 'recoil'
import { DetailIssueType } from 'page/detailIssuePage/DetailIssuePage';
import { timeChecker } from '../../../util/util';
import DetailIssueStatus from './DetailIssueStatus';
import HeaderViewMode from 'page/detailIssuePage/detailHeader/HeaderViewMode'
import HeaderEditMode from 'page/detailIssuePage/detailHeader/HeaderEditMode'
import { headerMode, detailTitle } from 'store/detailStore'
interface Props {
  issueData: DetailIssueType;
}

export default function DetailIssueHeader({
  issueData: { id, status, title, createdDateTime, comments },
}: Props) {
  
  const [pickedTitle, setPickedTitle] = useRecoilState(detailTitle)
  useEffect(()=>setPickedTitle(title),[])
  
  const { view } = useRecoilValue(headerMode)
  const passedTime = timeChecker(createdDateTime);
  const author = 'hayoung123'; // 임시 author api author필요
  const headerInfo = `이 이슈가 ${author}님에 의해 열렸습니다 ∙ 코멘트 ${comments.length} ∙ ${passedTime}`;
  return (
    <DetailIssueHeaderBlock>
      {view 
      ? <HeaderViewMode issueNumber={id} title={pickedTitle}/>
      : <HeaderEditMode issueNumber={id} title={pickedTitle}/>}
      <div className='header__description'>
        <DetailIssueStatus status={status} />
        <div className='issue__info'>{headerInfo}</div>
      </div>
    </DetailIssueHeaderBlock>
  );
}

const DetailIssueHeaderBlock = styled.div`
  padding-bottom: 2rem;
  border-bottom: ${({ theme }) => `1px solid ${theme.color.lineGrey}`};
  
  .header__description {
    display: flex;
    align-items: center;
    .issue__info {
      font-size: 1.2rem;
      margin-left: 8px;
    }
  }
  .header__edit__btn{
    display: flex;
  }
`;
