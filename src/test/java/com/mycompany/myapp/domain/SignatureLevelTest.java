package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class SignatureLevelTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SignatureLevel.class);
        SignatureLevel signatureLevel1 = new SignatureLevel();
        signatureLevel1.setId(1L);
        SignatureLevel signatureLevel2 = new SignatureLevel();
        signatureLevel2.setId(signatureLevel1.getId());
        assertThat(signatureLevel1).isEqualTo(signatureLevel2);
        signatureLevel2.setId(2L);
        assertThat(signatureLevel1).isNotEqualTo(signatureLevel2);
        signatureLevel1.setId(null);
        assertThat(signatureLevel1).isNotEqualTo(signatureLevel2);
    }
}
