import {Component, OnInit} from '@angular/core';
import {CodeType} from "../model/codeType";
import {CodeTypeService} from "../services/code-type.service";
import {ActivatedRoute} from "@angular/router";
import {CodeValue} from "../model/codeValue";

@Component({
  selector: 'app-code-type-details',
  templateUrl: './code-type-details.component.html',
  styleUrls: ['./code-type-details.component.scss']
})
export class CodeTypeDetailsComponent implements OnInit {
  codeType: CodeType;
  private id: number;

  constructor(private ctService: CodeTypeService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      try {
        let idParam = params.get("id");
        if (idParam) {
          // we are displaying an existing codetype
          this.id = parseInt(idParam);
          this.ctService.get(this.id).subscribe(data => this.codeType = data);
        } else {
          // we add a new codetype

        }
      } catch (e) {
        console.error("Invalid Code Type Id requested!", e);
      }
    });
  }

  addCodeType() {
    this.ctService.save(this.codeType)
      .subscribe(saved => this.id = parseInt(saved.id));
  }

  addCodeValue() {
    this.codeType.codeValues.push(new CodeValue());
  }
}
