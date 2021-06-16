import Foundation
import RxSwift
import RxCocoa

class IssueListViewModel: NSObject {
    
    private let storage = IssueMemoryStorage()
    private let filteredIssues = BehaviorRelay<[IssueInfo]>(value: [])
    let searchText = BehaviorRelay<String>(value: "")
    
    private var issueList: Driver<[IssueInfo]> {
        return storage.issueList()
    }
    
    override init() {
        super.init()
        setupSearchText()
        getIssueList()
    }
    
    func getIssueList() {
        guard let url = URL(string: API.getOpenIssue+"?status=open") else { return }
        APIService.get(url)
            .subscribe(onNext: { [weak self] issue in
                self?.storage.append(issue.issues)
            }, onError: { error in
                print(error)
            }).disposed(by: rx.disposeBag)
    }
    
    func issuList() -> Driver<[IssueInfo]> {
        return filteredIssues.asDriver(onErrorJustReturn: [])
    }
    
    func save(_ issue:IssueInfo) {
        if let index = storage.checkIndexRedundant(of: issue) {
            storage.update(issue, index)
        } else {
            storage.append([issue])
        }
    }
}

private extension IssueListViewModel {
    
    private func setupSearchText() {
        searchText.asObservable()
            .subscribe(onNext: { [weak self] text in
                self?.issueList.asObservable()
                    .map{$0.filter{$0.title.hasPrefix(text)}}
                    .bind(to: self?.filteredIssues ?? BehaviorRelay<[IssueInfo]>(value: []))
                    .disposed(by: self?.rx.disposeBag ?? DisposeBag())
            }).disposed(by: rx.disposeBag)
    }
}
