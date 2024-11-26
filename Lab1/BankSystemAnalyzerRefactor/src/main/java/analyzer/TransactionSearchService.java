package analyzer;

import model.BankTransaction;
import model.SearchCriteria;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionSearchService {

    public List<BankTransaction> searchByConditions(List<BankTransaction> transactions, SearchCriteria criteria) {
        return transactions.stream()
                .filter(transaction -> {
                    // Filter by start date if specified
                    if (criteria.getStartDate() != null && transaction.getDate().isBefore(criteria.getStartDate())) {
                        return false;
                    }
                    
                    // Filter by end date if specified
                    if (criteria.getEndDate() != null && transaction.getDate().isAfter(criteria.getEndDate())) {
                        return false;
                    }

                    // Filter by category if specified
                    if (criteria.getCategory() != null && !criteria.getCategory().equalsIgnoreCase(transaction.getDescription())) {
                        return false;
                    }

                    // Filter by minimum amount if specified
                    if (criteria.getMinAmount() != null) {
                        if (transaction.getAmount() < criteria.getMinAmount()) {
                            return false;
                        }
                    }

                    // Filter by maximum amount if specified
                    if (criteria.getMaxAmount() != null) {
                        if (transaction.getAmount() > criteria.getMaxAmount()) {
                            return false;
                        }
                    }

                    return true;
                })
                .collect(Collectors.toList());
    }
}
