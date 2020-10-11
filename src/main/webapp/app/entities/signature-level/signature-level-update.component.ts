import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISignatureLevel, SignatureLevel } from 'app/shared/model/signature-level.model';
import { SignatureLevelService } from './signature-level.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';

@Component({
  selector: 'jhi-signature-level-update',
  templateUrl: './signature-level-update.component.html',
})
export class SignatureLevelUpdateComponent implements OnInit {
  isSaving = false;
  statuses: IStatus[] = [];

  editForm = this.fb.group({
    id: [],
    orgName: [],
    orgId: [],
    level1: [],
    level2: [],
    level3: [],
    status: [],
  });

  constructor(
    protected signatureLevelService: SignatureLevelService,
    protected statusService: StatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ signatureLevel }) => {
      this.updateForm(signatureLevel);

      this.statusService.query().subscribe((res: HttpResponse<IStatus[]>) => (this.statuses = res.body || []));
    });
  }

  updateForm(signatureLevel: ISignatureLevel): void {
    this.editForm.patchValue({
      id: signatureLevel.id,
      orgName: signatureLevel.orgName,
      orgId: signatureLevel.orgId,
      level1: signatureLevel.level1,
      level2: signatureLevel.level2,
      level3: signatureLevel.level3,
      status: signatureLevel.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const signatureLevel = this.createFromForm();
    if (signatureLevel.id !== undefined) {
      this.subscribeToSaveResponse(this.signatureLevelService.update(signatureLevel));
    } else {
      this.subscribeToSaveResponse(this.signatureLevelService.create(signatureLevel));
    }
  }

  private createFromForm(): ISignatureLevel {
    return {
      ...new SignatureLevel(),
      id: this.editForm.get(['id'])!.value,
      orgName: this.editForm.get(['orgName'])!.value,
      orgId: this.editForm.get(['orgId'])!.value,
      level1: this.editForm.get(['level1'])!.value,
      level2: this.editForm.get(['level2'])!.value,
      level3: this.editForm.get(['level3'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISignatureLevel>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IStatus): any {
    return item.id;
  }
}
