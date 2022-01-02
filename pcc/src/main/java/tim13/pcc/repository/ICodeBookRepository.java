package tim13.pcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim13.pcc.model.CodeBook;

@Repository
public interface ICodeBookRepository extends JpaRepository<CodeBook, Long> {

	CodeBook findByIdentificationNumber(String substring);

}
