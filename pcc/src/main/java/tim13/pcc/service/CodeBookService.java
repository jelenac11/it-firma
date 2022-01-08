package tim13.pcc.service;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javassist.NotFoundException;
import tim13.pcc.dto.PCCRequestDTO;
import tim13.pcc.dto.PCCResponseDTO;
import tim13.pcc.model.CodeBook;
import tim13.pcc.repository.ICodeBookRepository;

@Service
public class CodeBookService {

	@Autowired
	private ICodeBookRepository codeBookRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(CodeBookService.class);

	public PCCResponseDTO pay(@Valid PCCRequestDTO pccRequestDto) throws NotFoundException {
		CodeBook cb = codeBookRepo.findByIdentificationNumber(pccRequestDto.getPAN().substring(0, 6));

		if (cb == null) {
			logger.trace("Nonexisting codebook");
			throw new NotFoundException("Invalid card data.");
		}

		RestTemplate rs = new RestTemplate();
		ResponseEntity<PCCResponseDTO> response = rs.postForEntity(cb.getUrl() + "/api/payment/pay", pccRequestDto,
				PCCResponseDTO.class);
		if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
			logger.trace("Invalid card data");
			throw new NotFoundException("Invalid credit card data.");
		}

		PCCResponseDTO pccResponse = response.getBody();

		return pccResponse;
	}

}
