import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SignatureApplicationTestModule } from '../../../test.module';
import { SignatureLevelComponent } from 'app/entities/signature-level/signature-level.component';
import { SignatureLevelService } from 'app/entities/signature-level/signature-level.service';
import { SignatureLevel } from 'app/shared/model/signature-level.model';

describe('Component Tests', () => {
  describe('SignatureLevel Management Component', () => {
    let comp: SignatureLevelComponent;
    let fixture: ComponentFixture<SignatureLevelComponent>;
    let service: SignatureLevelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SignatureApplicationTestModule],
        declarations: [SignatureLevelComponent],
      })
        .overrideTemplate(SignatureLevelComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SignatureLevelComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SignatureLevelService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SignatureLevel(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.signatureLevels && comp.signatureLevels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
