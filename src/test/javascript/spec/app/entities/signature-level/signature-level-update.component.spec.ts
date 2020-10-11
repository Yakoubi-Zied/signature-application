import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SignatureApplicationTestModule } from '../../../test.module';
import { SignatureLevelUpdateComponent } from 'app/entities/signature-level/signature-level-update.component';
import { SignatureLevelService } from 'app/entities/signature-level/signature-level.service';
import { SignatureLevel } from 'app/shared/model/signature-level.model';

describe('Component Tests', () => {
  describe('SignatureLevel Management Update Component', () => {
    let comp: SignatureLevelUpdateComponent;
    let fixture: ComponentFixture<SignatureLevelUpdateComponent>;
    let service: SignatureLevelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SignatureApplicationTestModule],
        declarations: [SignatureLevelUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SignatureLevelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SignatureLevelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SignatureLevelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SignatureLevel(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SignatureLevel();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
