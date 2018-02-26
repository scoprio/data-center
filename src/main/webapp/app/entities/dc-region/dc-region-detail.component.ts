import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DcRegion } from './dc-region.model';
import { DcRegionService } from './dc-region.service';

@Component({
    selector: 'jhi-dc-region-detail',
    templateUrl: './dc-region-detail.component.html'
})
export class DcRegionDetailComponent implements OnInit, OnDestroy {

    dcRegion: DcRegion;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dcRegionService: DcRegionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDcRegions();
    }

    load(id) {
        this.dcRegionService.find(id)
            .subscribe((dcRegionResponse: HttpResponse<DcRegion>) => {
                this.dcRegion = dcRegionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDcRegions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dcRegionListModification',
            (response) => this.load(this.dcRegion.id)
        );
    }
}
