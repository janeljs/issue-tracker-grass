import Foundation

enum ViewControllerID {
    static let issueFilter = "FilterVC"
    static let tabBar = "TabBarVC"
    static let redirection = "RedirectionVC"
    static let createIssue = "CreateIssueVC"
}

enum CellID {
    static let filter = "FilterCell"
}

enum API {
    static let getOpenIssue = "http://3.37.76.224/api/issues"
    static let githubLogin = URL(string: "https://github.com/login/oauth/authorize?client_id=65a58e22f27700054cb6&scope=user&redirect_uri=IssueTracker://tracker")
    static let accessToken = "http://3.37.76.224/api/login?code="
}

enum SectionTitle {
    static let status = "상태"
    static let author = "작성자"
    static let label = "레이블"
    static let milestone = "마일스톤"
}

enum SectionContent {
    static let opened = "열린 이슈"
    static let authorByMe = "내가 작성한 이슈"
    static let assignedToMe = "나에게 할당된 이슈"
    static let repliedByMe = "내가 댓글을 남긴 이슈"
    static let closed = "닫힌 이슈"
    static let noLabel = "레이블 없음"
}

enum TextView {
    static let placeHolder = "코멘트를 입력해주세요"
}
