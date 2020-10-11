import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SignatureApplicationTestModule } from '../../../test.module';
import { SignatureLevelDetailComponent } from 'app/entities/signature-level/signature-level-detail.component';
import { SignatureLevel } from 'app/shared/model/signature-level.model';

describe('Component Tests', () => {
  describe('SignatureLevel Management Detail Component', () => {
    let comp: SignatureLevelDetailComponent;
    let fixture: ComponentFixture<SignatureLevelDetailComponent>;
    const route = ({ data: of({ signatureLevel: new SignatureLevel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SignatureApplicationTestModule],
        declarations: [SignatureLevelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SignatureLevelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SignatureLevelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load signatureLevel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.signatureLevel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
