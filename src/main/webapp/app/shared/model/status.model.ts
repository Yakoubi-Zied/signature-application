import { ISignatureLevel } from 'app/shared/model/signature-level.model';
import { StatusEnum } from 'app/shared/model/enumerations/status-enum.model';

export interface IStatus {
  id?: number;
  status?: StatusEnum;
  exceptionContentType?: string;
  exception?: any;
  month?: number;
  year?: number;
  signatureLevels?: ISignatureLevel[];
}

export class Status implements IStatus {
  constructor(
    public id?: number,
    public status?: StatusEnum,
    public exceptionContentType?: string,
    public exception?: any,
    public month?: number,
    public year?: number,
    public signatureLevels?: ISignatureLevel[]
  ) {}
}
