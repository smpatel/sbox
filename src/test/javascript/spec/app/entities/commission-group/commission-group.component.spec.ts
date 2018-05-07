/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SboxTestModule } from '../../../test.module';
import { CommissionGroupComponent } from '../../../../../../main/webapp/app/entities/commission-group/commission-group.component';
import { CommissionGroupService } from '../../../../../../main/webapp/app/entities/commission-group/commission-group.service';
import { CommissionGroup } from '../../../../../../main/webapp/app/entities/commission-group/commission-group.model';

describe('Component Tests', () => {

    describe('CommissionGroup Management Component', () => {
        let comp: CommissionGroupComponent;
        let fixture: ComponentFixture<CommissionGroupComponent>;
        let service: CommissionGroupService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SboxTestModule],
                declarations: [CommissionGroupComponent],
                providers: [
                    CommissionGroupService
                ]
            })
            .overrideTemplate(CommissionGroupComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommissionGroupComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommissionGroupService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new CommissionGroup(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.commissionGroups[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
