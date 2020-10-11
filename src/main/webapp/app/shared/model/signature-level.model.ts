import { IStatus } from 'app/shared/model/status.model';

export interface ISignatureLevel {
  id?: number;
  orgName?: string;
  orgId?: string;
  level1?: number;
  level2?: number;
  level3?: number;
  completed?: boolean;
  status?: IStatus;
}

export class SignatureLevel implements ISignatureLevel {
  constructor(
    public id?: number,
    public orgName?: string,
    public orgId?: string,
    public level1?: number,
    public level2?: number,
    public level3?: number,
    public completed?: boolean,
    public status?: IStatus
  ) {
    this.completed = this.completed || false;
  }
}
