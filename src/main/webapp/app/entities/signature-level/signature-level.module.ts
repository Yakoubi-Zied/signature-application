import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SignatureApplicationSharedModule } from 'app/shared/shared.module';
import { SignatureLevelComponent } from './signature-level.component';
import { SignatureLevelDetailComponent } from './signature-level-detail.component';
import { SignatureLevelUpdateComponent } from './signature-level-update.component';
import { SignatureLevelDeleteDialogComponent } from './signature-level-delete-dialog.component';
import { signatureLevelRoute } from './signature-level.route';

@NgModule({
  imports: [SignatureApplicationSharedModule, RouterModule.forChild(signatureLevelRoute)],
  declarations: [
    SignatureLevelComponent,
    SignatureLevelDetailComponent,
    SignatureLevelUpdateComponent,
    SignatureLevelDeleteDialogComponent,
  ],
  entryComponents: [SignatureLevelDeleteDialogComponent],
})
export class SignatureApplicationSignatureLevelModule {}
