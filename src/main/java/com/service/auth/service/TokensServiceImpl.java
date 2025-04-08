package com.service.auth.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.auth.builder.response.CountResponse;
import com.service.auth.builder.response.DatatableResponse;
import com.service.auth.builder.response.MessageResponse;
import com.service.auth.model.Tokens;
import com.service.auth.repository.TokensRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Service
public class TokensServiceImpl implements TokensService {

    @Autowired
    private MessageService messageService;

    @Autowired
    private TokensRepository tokensRepository;
    
	public ResponseEntity<?> listcount(Locale locale) {
		
		try {
			long count = tokensRepository.count();
			return ResponseEntity.ok(new CountResponse(count));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> list(Locale locale, boolean all, int page, int size, String search, String sortcolumn, boolean descending, int draw, String username) {

		try {
			if (all) {
				List<Tokens> tokenslist = tokensRepository.findAll();
				return ResponseEntity.ok(tokenslist);
			}

			Page<Tokens> tokenspage = null;
			long totalrows = tokensRepository.count();
			long recordsFiltered = totalrows;

			Specification<Tokens> spec = username != null ? JPASpecification.returnUserTokenSpecification(search, sortcolumn, descending, username) :
					JPASpecification.returnTokenSpecification(search, sortcolumn, descending);
		    Pageable pageable = PageRequest.of(page, size);
		    tokenspage = tokensRepository.findAll(spec, pageable);
		    
			if (search != null && !search.trim().equals("")) {
				List<Tokens> allusersbysearch = tokensRepository.findAll(spec);
				recordsFiltered = allusersbysearch.size();
			} 
	
	        List<Tokens> list = new ArrayList<Tokens>(tokenspage.getContent());
	        
	        DatatableResponse<Tokens> datatableresponse = new DatatableResponse<Tokens>(draw, totalrows, recordsFiltered, list);
		       
			return ResponseEntity.ok(datatableresponse);
	        
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public ResponseEntity<?> revoke(Locale locale, String token, String refreshkey, String reason) {

		try {

			String username = new String(Base64.getDecoder().decode(refreshkey));

			List<Tokens> tokens = token == null ? tokensRepository.findByUsername(username) : tokensRepository.findByRefreshtokenAndUsername(token, username);
			if (tokens != null && tokens.size() > 0) {
				for (Tokens t : tokens) {
					if (t.getRevoked_date_time() == null) {
						t.setRevoked_date_time(new Date());
						t.setReason(reason);
						tokensRepository.save(t);
					}
				}
			} else 
				return ResponseEntity.ok(new MessageResponse(messageService.getMessage("invalid_token", locale), 115));

			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("revoked_token", locale)));
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new MessageResponse(messageService.getMessage("exception_case", locale), 111));
		}
	}

	@Override
	public List<Tokens> findByAccesstokenAndUsername(String token, String username) {

		try {
			return tokensRepository.findByAccesstokenAndUsername(token, username);
	
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Tokens>();
		}
	}

	@Override
	public List<Tokens> findByRefreshtokenAndUsername(String token, String username) {

		try {
			return tokensRepository.findByRefreshtokenAndUsername(token, username);
	
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Tokens>();
		}
	}

	@Override
	public Tokens save(Tokens tokens) {
		try {
			return tokensRepository.save(tokens);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tokens;
		
	}

	@Override
	public Tokens findById(String id) {
		try {
			Optional<Tokens> token = tokensRepository.findById(id);
			if (token.isPresent())
				return token.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
