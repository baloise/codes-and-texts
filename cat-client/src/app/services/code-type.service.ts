import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CodeType} from '../model/codeType';
import {environment} from '../../environments/environment';
import {map} from 'rxjs/operators';

interface CodeTypeResponse {
  links: any;
  content: CodeType[];
}

@Injectable({
  providedIn: 'root'
})
export class CodeTypeService {

  private readonly serviceBaseUrl: string;

  constructor(private http: HttpClient) {
    this.serviceBaseUrl = environment.serviceBase + 'codeType/';
  }

  public findAll(page: number = 0): Observable<CodeType[]> {
    return this.http.get<CodeTypeResponse>(this.serviceBaseUrl, {params: {page: String(page)}})
      .pipe(map(r => r.content));
  }

  public findByResponsiblePrefix(prefix: string, page: number = 0): Observable<CodeType[]> {
    return this.http.get<CodeTypeResponse>(this.serviceBaseUrl + 'search/findByResponsiblePrefix', {
      params: {
        prefix,
        page: String(page)
      }
    })
      .pipe(map(r => r.content));
  }

  public get(id: number) {
    return this.http.get<CodeType>(this.serviceBaseUrl + id, {params: {projection: 'inlineCodeValue'}});
  }

  public save(ct: CodeType) {
    return this.http.post<CodeType>(this.serviceBaseUrl, ct);
  }
}
