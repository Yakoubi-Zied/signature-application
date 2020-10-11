import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISignatureLevel } from 'app/shared/model/signature-level.model';
import { SignatureLevelService } from './signature-level.service';
import { SignatureLevelDeleteDialogComponent } from './signature-level-delete-dialog.component';

@Component({
  selector: 'jhi-signature-level',
  templateUrl: './signature-level.component.html',
})
export class SignatureLevelComponent implements OnInit, OnDestroy {
  signatureLevels?: ISignatureLevel[];
  eventSubscriber?: Subscription;

  constructor(
    protected signatureLevelService: SignatureLevelService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.signatureLevelService.query().subscribe((res: HttpResponse<ISignatureLevel[]>) => (this.signatureLevels = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSignatureLevels();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISignatureLevel): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSignatureLevels(): void {
    this.eventSubscriber = this.eventManager.subscribe('signatureLevelListModification', () => this.loadAll());
  }

  delete(signatureLevel: ISignatureLevel): void {
    const modalRef = this.modalService.open(SignatureLevelDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.signatureLevel = signatureLevel;
  }
}
