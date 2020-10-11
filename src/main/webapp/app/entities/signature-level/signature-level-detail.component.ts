import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISignatureLevel } from 'app/shared/model/signature-level.model';

@Component({
  selector: 'jhi-signature-level-detail',
  templateUrl: './signature-level-detail.component.html',
})
export class SignatureLevelDetailComponent implements OnInit {
  signatureLevel: ISignatureLevel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ signatureLevel }) => (this.signatureLevel = signatureLevel));
  }

  previousState(): void {
    window.history.back();
  }
}
