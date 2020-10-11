import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISignatureLevel } from 'app/shared/model/signature-level.model';

type EntityResponseType = HttpResponse<ISignatureLevel>;
type EntityArrayResponseType = HttpResponse<ISignatureLevel[]>;

@Injectable({ providedIn: 'root' })
export class SignatureLevelService {
  public resourceUrl = SERVER_API_URL + 'api/signature-levels';

  constructor(protected http: HttpClient) {}

  create(signatureLevel: ISignatureLevel): Observable<EntityResponseType> {
    return this.http.post<ISignatureLevel>(this.resourceUrl, signatureLevel, { observe: 'response' });
  }

  update(signatureLevel: ISignatureLevel): Observable<EntityResponseType> {
    return this.http.put<ISignatureLevel>(this.resourceUrl, signatureLevel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISignatureLevel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISignatureLevel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
