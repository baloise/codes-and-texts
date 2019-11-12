import {Component, OnInit} from '@angular/core';
import {CodeTypeService} from '../services/code-type.service';
import {CodeType} from '../model/codeType';

@Component({
  selector: 'app-code-type-list',
  templateUrl: './code-type-list.component.html',
  styleUrls: ['./code-type-list.component.scss']
})
export class CodeTypeListComponent implements OnInit {

  private codeTypes: CodeType[];
  filter: string;
  currentpage = 0;

  constructor(private ctService: CodeTypeService) {
  }

  ngOnInit() {
    this.update();
  }

  nextPage() {
    this.currentpage++;
    this.update();
  }

  previousPage() {
    this.currentpage--;
    if (this.currentpage < 0) {
      this.currentpage = 0;
    }
    this.update();
  }

  search() {
    this.update();
    this.currentpage = 0;
  }

  update() {
    if (this.filter) {
      this.ctService.findByResponsiblePrefix(this.filter, this.currentpage).subscribe(data => this.codeTypes = data);
    } else {
      this.ctService.findAll(this.currentpage).subscribe(data => this.codeTypes = data);
    }
  }
}
