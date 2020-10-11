import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISignatureLevel } from 'app/shared/model/signature-level.model';
import { SignatureLevelService } from './signature-level.service';

@Component({
  templateUrl: './signature-level-delete-dialog.component.html',
})
export class SignatureLevelDeleteDialogComponent {
  signatureLevel?: ISignatureLevel;

  constructor(
    protected signatureLevelService: SignatureLevelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.signatureLevelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('signatureLevelListModification');
      this.activeModal.close();
    });
  }
}
