package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.SignatureLevel;
import com.mycompany.myapp.repository.SignatureLevelRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.SignatureLevel}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SignatureLevelResource {

    private final Logger log = LoggerFactory.getLogger(SignatureLevelResource.class);

    private static final String ENTITY_NAME = "signatureLevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SignatureLevelRepository signatureLevelRepository;

    public SignatureLevelResource(SignatureLevelRepository signatureLevelRepository) {
        this.signatureLevelRepository = signatureLevelRepository;
    }

    /**
     * {@code POST  /signature-levels} : Create a new signatureLevel.
     *
     * @param signatureLevel the signatureLevel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new signatureLevel, or with status {@code 400 (Bad Request)} if the signatureLevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/signature-levels")
    public ResponseEntity<SignatureLevel> createSignatureLevel(@RequestBody SignatureLevel signatureLevel) throws URISyntaxException {
        log.debug("REST request to save SignatureLevel : {}", signatureLevel);
        if (signatureLevel.getId() != null) {
            throw new BadRequestAlertException("A new signatureLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SignatureLevel result = signatureLevelRepository.save(signatureLevel);
        return ResponseEntity.created(new URI("/api/signature-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /signature-levels} : Updates an existing signatureLevel.
     *
     * @param signatureLevel the signatureLevel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated signatureLevel,
     * or with status {@code 400 (Bad Request)} if the signatureLevel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the signatureLevel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/signature-levels")
    public ResponseEntity<SignatureLevel> updateSignatureLevel(@RequestBody SignatureLevel signatureLevel) throws URISyntaxException {
        log.debug("REST request to update SignatureLevel : {}", signatureLevel);
        if (signatureLevel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SignatureLevel result = signatureLevelRepository.save(signatureLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, signatureLevel.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /signature-levels} : get all the signatureLevels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of signatureLevels in body.
     */
    @GetMapping("/signature-levels")
    public List<SignatureLevel> getAllSignatureLevels() {
        log.debug("REST request to get all SignatureLevels");
        return signatureLevelRepository.findAll();
    }

    /**
     * {@code GET  /signature-levels/:id} : get the "id" signatureLevel.
     *
     * @param id the id of the signatureLevel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the signatureLevel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/signature-levels/{id}")
    public ResponseEntity<SignatureLevel> getSignatureLevel(@PathVariable Long id) {
        log.debug("REST request to get SignatureLevel : {}", id);
        Optional<SignatureLevel> signatureLevel = signatureLevelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(signatureLevel);
    }

    /**
     * {@code DELETE  /signature-levels/:id} : delete the "id" signatureLevel.
     *
     * @param id the id of the signatureLevel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/signature-levels/{id}")
    public ResponseEntity<Void> deleteSignatureLevel(@PathVariable Long id) {
        log.debug("REST request to delete SignatureLevel : {}", id);
        signatureLevelRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
