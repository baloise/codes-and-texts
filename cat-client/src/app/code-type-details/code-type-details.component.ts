import {Component, OnInit} from '@angular/core';
import {CodeType} from "../model/codeType";
import {CodeTypeService} from "../services/code-type.service";
import {ActivatedRoute} from "@angular/router";

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
          this.id = parseInt(idParam);
          this.ctService.get(this.id).subscribe(data => {
            this.codeType = data
            console.log(this.codeType)
          });
        }
      } catch (e) {
        console.error("Invalid Code Type Id requested!", e);
      }
    })
  }
}
