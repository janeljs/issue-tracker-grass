import { atom, selector } from 'recoil';
interface countType {
  label: number;
  milestone: number;
  openedIssue: number;
  closedIssue: number;
}

type LabelsProp = {
  id: number;
  name: string;
  colorCode: string;
  description: string;
  checked: boolean;
};

interface IssuesType {
  assignees: string[];
  author: string;
  comment: string;
  commentNumber: number;
  createdDateTime: string;
  id: number;
  labels: LabelsProp[];
  milestone: string;
  title: string;
}

interface IssuesInfoStateType {
  issues: IssuesType[];
  count: countType;
}

export const issueTypeState = atom<string>({
  key: 'issueTypeState',
  default: 'open',
});

export const issueFilterTypeState = atom<string>({
  key: 'issueFilterTypeState',
  default: '',
});

export const getIssuesInfoState = selector<IssuesInfoStateType>({
  key: 'GET/issues',
  get: async ({ get }) => {
    const issueType = get(issueTypeState);
    try {
      const response = await fetch(`http://3.37.76.224/api/issues?status=${issueType}`);
      const issuesData = await response.json();
      const issuesInfoState = { issues: issuesData.issues, count: issuesData.count };
      return issuesInfoState;
    } catch (err) {
      throw new Error('잘못된 요청입니다.');
    }
  },
});

const headerInfo = {
  // headers: {
  //   Authorization: Bearer {token}
  // }
};
