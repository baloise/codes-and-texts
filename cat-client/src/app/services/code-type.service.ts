import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CodeType} from "../model/codeType";
import {environment} from "../../environments/environment";
import {map} from "rxjs/operators";

type CodeTypeResponse = { links: any, content: CodeType[] };

@Injectable({
  providedIn: 'root'
})
export class CodeTypeService {

  private serviceBaseUrl: string;

  constructor(private http: HttpClient) {
    this.serviceBaseUrl = environment.serviceBase + "codeType/";
  }

  public findAll(): Observable<CodeType[]> {
    return this.http.get<CodeTypeResponse>(this.serviceBaseUrl)
      .pipe(map(r => r.content));
  }

  public findByResponsiblePrefix(prefix: string): Observable<CodeType[]> {
    return this.http.get<CodeTypeResponse>(this.serviceBaseUrl + "search/findByResponsiblePrefix", {params: {"prefix": prefix}})
      .pipe(map(r => r.content));
    ;
  }

  public save(ct: CodeType) {
    return this.http.post<CodeType>(this.serviceBaseUrl, ct);
  }
}
