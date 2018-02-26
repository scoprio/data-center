import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DcAuthority } from './dc-authority.model';
import { DcAuthorityService } from './dc-authority.service';

@Component({
    selector: 'jhi-dc-authority-detail',
    templateUrl: './dc-authority-detail.component.html'
})
export class DcAuthorityDetailComponent implements OnInit, OnDestroy {

    dcAuthority: DcAuthority;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dcAuthorityService: DcAuthorityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDcAuthorities();
    }

    load(id) {
        this.dcAuthorityService.find(id)
            .subscribe((dcAuthorityResponse: HttpResponse<DcAuthority>) => {
                this.dcAuthority = dcAuthorityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDcAuthorities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dcAuthorityListModification',
            (response) => this.load(this.dcAuthority.id)
        );
    }
}
