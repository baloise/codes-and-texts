import {Component, OnInit} from '@angular/core';
import {CodeType} from '../model/codeType';
import {CodeTypeService} from '../services/code-type.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-code-type-form',
  templateUrl: './code-type-form.component.html',
  styleUrls: ['./code-type-form.component.scss']
})
export class CodeTypeFormComponent implements OnInit {

  id: number;
  codeType: CodeType = new CodeType();
  referrerUrl: string;

  constructor(private ctService: CodeTypeService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        // we have an id param - this is edit-mode
        try {
          this.id = parseInt(idParam, 10);
          this.ctService.get(this.id).subscribe(data => this.codeType = data);
        } catch (e) {
          console.error('Invalid Code Type Id requested!', e);
        }
      } else {
        // this is add-mode
      }
    });

    this.route.queryParams.subscribe(params => {
      this.referrerUrl = params.referrer || '/codetypes';
    });
  }

  save() {
    console.log(this.codeType);
  }
}
