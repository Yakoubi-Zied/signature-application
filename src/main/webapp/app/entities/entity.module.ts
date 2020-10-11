import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'signature-level',
        loadChildren: () => import('./signature-level/signature-level.module').then(m => m.SignatureApplicationSignatureLevelModule),
      },
      {
        path: 'status',
        loadChildren: () => import('./status/status.module').then(m => m.SignatureApplicationStatusModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SignatureApplicationEntityModule {}
