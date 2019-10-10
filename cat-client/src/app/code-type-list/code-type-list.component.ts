import {Component, OnInit} from '@angular/core';
import {CodeTypeService} from "../services/code-type.service";
import {CodeType} from "../model/codeType";

@Component({
  selector: 'app-code-type-list',
  templateUrl: './code-type-list.component.html',
  styleUrls: ['./code-type-list.component.scss']
})
export class CodeTypeListComponent implements OnInit {

  private codeTypes: CodeType[];

  constructor(private ctService: CodeTypeService) {
  }

  ngOnInit() {
    this.ctService.findAll()
      .subscribe(data => this.codeTypes = data);
  }
}
