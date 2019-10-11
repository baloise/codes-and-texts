import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CodeTypeListComponent} from "./code-type-list/code-type-list.component";
import {CodeTypeDetailsComponent} from "./code-type-details/code-type-details.component";

const routes: Routes = [
  { path: '', component: CodeTypeListComponent },
  { path: 'codetypes', component: CodeTypeListComponent },
  { path: 'codetype', component: CodeTypeDetailsComponent },
  { path: 'codetype/:id', component: CodeTypeDetailsComponent },
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
